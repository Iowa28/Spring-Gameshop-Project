package ru.kpfu.aminovniaz.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import ru.kpfu.aminovniaz.project.model.Game;
import ru.kpfu.aminovniaz.project.model.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Форма пользователя")
public class UserForm {
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    @ApiModelProperty(value = "Имя пользователя", example = "Вася Пупкин")
    private String username;
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    @Email(message = "Некорректный email адрес.")
    @ApiModelProperty(value = "Электронный адрес", example = "vasyapupkin@mail.ru")
    private String email;
    @NotNull
    @NotEmpty(message = "Это поле не может быть пустым.")
    @ApiModelProperty(value = "Пароль", example = "qwerty123")
    private String password;

    @ApiModelProperty(value = "Деньги", example = "0")
    private int money;
    @ApiModelProperty(value = "Список приобретенных игр")
    private List<String> purchasedGames;

    public static UserForm from(User user) {
        UserForm userForm = UserForm.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .money(user.getCurrentMoney())
                .build();

        if (user.getPurchasedGames() != null) {
            userForm.setPurchasedGames(user.getPurchasedGames().stream().map(Game::getName).collect(Collectors.toList()));
        }
        return userForm;
    }

    public static List<UserForm> from(List<User> users) {
        return users.stream().map(UserForm::from).collect(Collectors.toList());
    }
}
