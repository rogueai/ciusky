package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.CiuskySearchService;
import dev.rogueai.collection.service.CiuskyService;
import dev.rogueai.collection.service.ImageService;
import dev.rogueai.collection.service.OptionService;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.CiuskyFilter;
import dev.rogueai.collection.service.model.CiuskySearch;
import dev.rogueai.collection.service.model.Option;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxReselect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class CiuskyController {

    private static final Log logger = LogFactory.getLog(CiuskyController.class);

    @Autowired
    private CiuskySearchService ciuskySearchService;

    @Autowired
    private CiuskyService ciuskyService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ImageService imageService;

    @GetMapping({ "/" })
    public String list(Model model) {
        CiuskyFilter filter = new CiuskyFilter("", null);
        List<CiuskySearch> listCiusky = ciuskySearchService.findAll(filter);
        model.addAttribute("ciuskyFilter", filter);
        model.addAttribute("listCiusky", listCiusky);
        return "index";
    }

    @HxRequest()
    @PostMapping({ "/search" })
    public String search(@ModelAttribute CiuskyFilter filter, Model model) {
        logger.info("Search Endpoint invoked with params: " + filter);

        List<CiuskySearch> listCiusky = ciuskySearchService.findAll(filter);
        model.addAttribute("listCiusky", listCiusky);
        return "ciusky-table";
    }

    @GetMapping({ "/ciusky", "/ciusky/{id}" })
    public String create(@PathVariable(required = false) Long id, Model model) {
        List<Option> types = optionService.types();
        model.addAttribute("types", types);
        Ciusky ciusky = id != null ? ciuskyService.get(id) : new Ciusky();
        model.addAttribute("ciusky", ciusky);
        return "create";
    }

    @HxRequest
    @HxReselect("form")
    @PostMapping({ "/ciusky" })
    public String save(@ModelAttribute Ciusky ciusky, Model model, HtmxResponse htmxResponse) {
        ciuskyService.save(ciusky);

        List<Option> types = optionService.types();
        model.addAttribute("types", types);
        model.addAttribute("ciusky", ciusky);

        // We tell html to trigger a custom event showToast (see main.js)
        htmxResponse.addTriggerAfterSettle("showToast", new ToastMessage(true, "Ciusky aggiornato! GG"));

        /*
         TODO: instead of returning the entire page we should return only the updated form template
           for now the with @HxReselect("form") we tell htmx to take from the response only the tag form
         */
        return "create";
    }

    /**
     * Delete a Ciusky. Responds with a 200 status code and empty content, indicating that the row should be replaced with nothing.
     *
     * @param id ciusky id
     * @return OK
     */
    @HxRequest()
    @DeleteMapping({ "/ciusky/{id}" })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ciuskyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping({ "/image/{uuid}" })
    public @ResponseBody byte[] image(@PathVariable String uuid, @RequestParam(required = false, defaultValue = "false") boolean thumbnail) throws IOException {
        return imageService.getResource(uuid, thumbnail);
    }

    /**
     * TODO: For now this works only works for htmx
     */
    @HxRequest()
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleError(Exception ex, HtmxResponse htmxResponse) {
        htmxResponse.setReswap(HtmxReswap.none());
        htmxResponse.addTriggerAfterSettle("showToast", new ToastMessage(false, "TUTTO ROTTO"));
        return ResponseEntity.ok().build();
    }
}
