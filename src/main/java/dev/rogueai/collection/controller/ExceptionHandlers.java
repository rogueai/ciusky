package dev.rogueai.collection.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@ControllerAdvice
public class ExceptionHandlers {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @ExceptionHandler({ NoHandlerFoundException.class, NoResourceFoundException.class })
    public String noHandler(Exception ex, HtmxResponse htmxResponse) {
        htmxResponse.setReswap(HtmxReswap.none());
        return "404";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> generic(Exception ex, HtmxRequest htmxRequest, HtmxResponse htmxResponse) {
        if (htmxRequest.isHtmxRequest()) {
            htmxResponse.setReswap(HtmxReswap.none());

            Context context = new Context();
            context.setVariable("toast", new ToastMessage(false, "An error occurred while processing your request"));
            String text = templateEngine.process("toast", context);
            htmxResponse.addTrigger("showToast", text);

            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.internalServerError().build();
    }

}
