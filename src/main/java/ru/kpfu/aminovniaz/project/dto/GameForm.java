package ru.kpfu.aminovniaz.project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Lob;
import javax.validation.constraints.*;

@Data
public class GameForm {
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String name;

    @Lob
    @NotEmpty(message = "У игры должно быть какое-то поисание.")
    private String annotation;

    @URL(message = "Некорректный URL.")
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String cover;

    @Min(value = 50, message = "Слишком низкая цена.")
    @Max(value = 5000, message = "Слишком высокая цена.")
    private Integer cost;

    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String genre;

    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String releaseDate;
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String developer;
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String publisher;
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    private String steamId;
}
