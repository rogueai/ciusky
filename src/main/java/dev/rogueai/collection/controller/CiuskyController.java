package dev.rogueai.collection.controller;

import dev.rogueai.collection.service.CiuskyNotFoundException;
import dev.rogueai.collection.service.CiuskySearchService;
import dev.rogueai.collection.service.CiuskyService;
import dev.rogueai.collection.service.ImageService;
import dev.rogueai.collection.service.OptionService;
import dev.rogueai.collection.service.TagService;
import dev.rogueai.collection.service.model.Book;
import dev.rogueai.collection.service.model.Ciusky;
import dev.rogueai.collection.service.model.CiuskyFilter;
import dev.rogueai.collection.service.model.CiuskyImage;
import dev.rogueai.collection.service.model.CiuskySearch;
import dev.rogueai.collection.service.model.ECiuskyType;
import dev.rogueai.collection.service.model.Option;
import dev.rogueai.collection.service.model.Tag;
import dev.rogueai.collection.util.TemplateUtils;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxReselect;
import jakarta.validation.Valid;
import org.apache.commons.lang3.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CiuskyController {

    private static final Log logger = LogFactory.getLog(CiuskyController.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private CiuskySearchService ciuskySearchService;

    @Autowired
    private CiuskyService ciuskyService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TagService tagService;

    @GetMapping({ "/" })
    public String list(Model model) {
        List<Option> types = optionService.types();
        // TODO: every time we go back to the home page the filters are inizialized again
        // TODO: we should store the CiuskyFilter in session?
        CiuskyFilter filter = new CiuskyFilter("", types);
        List<CiuskySearch> listCiusky = ciuskySearchService.findAll(filter);
        model.addAttribute("ciuskyFilter", filter);
        model.addAttribute("ciuskyTypes", types);
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
        try {
            Ciusky ciusky = id != null ? ciuskyService.get(id) : new Ciusky();
            model.addAttribute("ciusky", ciusky);
            List<Option> types = optionService.types();
            model.addAttribute("types", types);
            return "create";
        } catch (CiuskyNotFoundException e) {
            logger.warn(e.getMessage(), e);
            return "redirect:/";
        }
    }

    /**
     * @see TemplateUtils getSaveAction
     */
    @HxRequest
    @HxReselect("#forms")
    @PostMapping({ "/ciusky/saveCiusky" })
    public String saveCiusky(@ModelAttribute @Valid Ciusky ciusky, BindingResult bindingResult, Model model, HtmxResponse htmxResponse) {
        return save(ciusky, bindingResult, model, htmxResponse);
    }

    /**
     * @see TemplateUtils getSaveAction
     */
    @HxRequest
    @HxReselect("#forms")
    @PostMapping({ "/ciusky/saveBook" })
    public String saveBook(@Valid @ModelAttribute(value = "ciusky") Book ciusky, BindingResult bindingResult, Model model, HtmxResponse htmxResponse) {
        return save(ciusky, bindingResult, model, htmxResponse);
    }

    public <T extends Ciusky> String save(T ciusky, BindingResult bindingResult, Model model, HtmxResponse htmxResponse) {

        if (bindingResult.hasErrors()) {
            // TODO: this is bad, we are forced to reload everything because the list of images are in the othe form
            // TODO: if we fix this we can remove addAttribute

            List<Option> types = optionService.types();
            model.addAttribute("types", types);

            List<CiuskyImage> images = ciuskyService.getImages(ciusky.getId());
            ciusky.setImages(images);
            model.addAttribute("ciusky", ciusky);
            return "create";
        }

        ciuskyService.save(ciusky);

        // TODO: this is bad, we are forced to reload everything because the list of images are in the othe form
        // TODO: if we fix this we can remove addAttribute
        List<Option> types = optionService.types();
        model.addAttribute("types", types);

        List<CiuskyImage> images = ciuskyService.getImages(ciusky.getId());
        ciusky.setImages(images);

        model.addAttribute("ciusky", ciusky);
        htmxResponse.addTrigger("showToast", createToast(true, "Ciusky aggiornato! GG"));
        /*
         TODO: instead of returning the entire page we should return only the updated form template
           for now the with @HxReselect("form") we tell htmx to take from the response only the tag form
         */
        return "create";

    }

    @HxRequest
    @HxReselect("#forms")
    @PutMapping({ "/ciusky/changeType" })
    public String changeType(@ModelAttribute Ciusky ciusky, Model model) {

        List<Option> types = optionService.types();
        model.addAttribute("types", types);

        if (ECiuskyType.from(ciusky.getType()) == ECiuskyType.BOOK) {
            Book book = new Book(ciusky);
            if (ciusky.getId() != null) {
                book.setImages(ciuskyService.getImages(ciusky.getId()));
            }
            model.addAttribute("ciusky", book);
        } else {
            if (ciusky.getId() != null) {
                ciusky.setImages(ciuskyService.getImages(ciusky.getId()));
            }
            model.addAttribute("ciusky", ciusky);
        }

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

    @GetMapping(value = { "/ciusky/image/{uuid}" }, produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody byte[] image(@PathVariable String uuid, @RequestParam(required = false, defaultValue = "false") boolean thumbnail) throws IOException {
        return imageService.getResource(uuid, thumbnail);
    }

    @HxRequest()
    @PostMapping({ "/ciusky/image/upload" })
    public String imageUpload(@ModelAttribute Ciusky ciusky, @RequestParam("imageFiles") MultipartFile[] files, Model model) throws IOException, CiuskyNotFoundException {
        for (MultipartFile file : files) {
            imageService.addResource(ciusky.getId(), file.getName(), file.getBytes());
        }
        model.addAttribute("ciusky", ciuskyService.get(ciusky.getId()));
        return "components/gallery :: gallery";
    }

    @HxRequest()
    @DeleteMapping({ "/ciusky/{id}/image/{uuid}" })
    public String imageDelete(@PathVariable(required = true) Long id, @PathVariable(required = true) String uuid, Model model) throws CiuskyNotFoundException {
        imageService.delete(id, uuid);
        model.addAttribute("ciusky", ciuskyService.get(id));
        return "components/gallery :: gallery";
    }

    @HxRequest()
    @GetMapping({ "/ciusky/tag/search" })
    public String tagSearch(@RequestParam String rawTag, Model model) {
        if (Strings.CI.contains(rawTag, ":")) {
            String[] split = rawTag.split(":");
            String key = split[0];
            String value = split.length > 1 ? split[1] : null;
            List<String> values = tagService.getValues(key, value);
            model.addAttribute("tagSearchResult", values.stream().map(v -> key + ":" + v).collect(Collectors.toList()));
        } else {
            List<String> keys = tagService.getKeys(rawTag);
            model.addAttribute("tagSearchResult", keys);
        }
        return "components/tag-search";
    }

    @HxRequest()
    @PostMapping({ "/ciusky/tag/add" })
    public String tagAdd(@ModelAttribute Ciusky ciusky, HtmxResponse htmxResponse) {
        String tag = ciusky.getRawTag();
        String[] split = tag.split(":");

        if (Strings.CI.contains(tag, ":") && split.length == 2) {
            ciusky.getTags().add(new Tag(split[0], split[1]));
            ciusky.setRawTag(null);
        } else {
            // TODO: Whe should show a validation error on the input field instead.
            htmxResponse.addTrigger("showToast", createToast(false, "Invalid Tag"));
        }
        return "components/tag-input";
    }

    @HxRequest()
    @PostMapping({ "/ciusky/tag/delete/{id}" })
    public String tagDelete(@PathVariable int id, @ModelAttribute Ciusky ciusky) {
        ciusky.getTags().remove(id);
        return "components/tag-input";
    }

    private String createToast(boolean success, String message) {
        Context context = new Context();
        context.setVariable("toast", new ToastMessage(success, message));
        return templateEngine.process("toast", context);
    }

}
