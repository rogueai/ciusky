package dev.rogueai.ciusky.controller;

import dev.rogueai.ciusky.util.TemplateUtils;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionHandlers {

    private static final Log logger = LogFactory.getLog(ExceptionHandlers.class);

    @Autowired
    private TemplateUtils templateUtils;

    @ExceptionHandler({ NoHandlerFoundException.class, NoResourceFoundException.class })
    public String noHandler(Exception ex, HtmxResponse htmxResponse) {
        logger.error(ex.getMessage(), ex);
        htmxResponse.setReswap(HtmxReswap.none());
        return "404";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> generic(Exception ex, HtmxRequest htmxRequest, HtmxResponse htmxResponse) {
        logger.error(ex.getMessage(), ex);
        if (htmxRequest.isHtmxRequest()) {
            htmxResponse.setReswap(HtmxReswap.none());
            templateUtils.toast(htmxResponse, false, "An error occurred while processing your request", ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.internalServerError().build();
    }

}
