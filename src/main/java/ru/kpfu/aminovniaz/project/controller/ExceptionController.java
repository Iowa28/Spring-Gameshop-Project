package ru.kpfu.aminovniaz.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.aminovniaz.project.exception.NotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView serverError() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error/500");
        return model;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound(Exception exception) {
        ModelAndView model = new ModelAndView();
        model.addObject("exception", exception);
        model.setViewName("error/404");
        return model;
    }
}
