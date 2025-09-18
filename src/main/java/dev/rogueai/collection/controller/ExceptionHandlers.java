package dev.rogueai.collection.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({ NoHandlerFoundException.class, NoResourceFoundException.class })
    public String noHandler(Exception ex, HtmxResponse htmxResponse) {
        htmxResponse.setReswap(HtmxReswap.none());
        return "404";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> generic(Exception ex, HtmxRequest htmxRequest, HtmxResponse htmxResponse) {
        if (htmxRequest.isHtmxRequest()) {
            htmxResponse.setReswap(HtmxReswap.none());
            htmxResponse.addTriggerAfterSettle("showToast", new ToastMessage(false, "An error occurred while processing your request"));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }

}
