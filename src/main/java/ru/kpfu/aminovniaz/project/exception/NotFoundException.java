package ru.kpfu.aminovniaz.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private String entity = "страница";

    public NotFoundException(){
        super();
    }

    public NotFoundException(String entity) {
        super();
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }
}
