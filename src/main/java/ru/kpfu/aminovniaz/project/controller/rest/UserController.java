package ru.kpfu.aminovniaz.project.controller.rest;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.aminovniaz.project.dto.UserForm;
import ru.kpfu.aminovniaz.project.service.SignUpService;
import ru.kpfu.aminovniaz.project.service.UserService;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "У вас недостаточно прав"),
        @ApiResponse(code = 401, message = "Вы не авторизованы"),
        @ApiResponse(code = 404, message = "Пользователь не найден"),
        @ApiResponse(code = 500, message = "У сервера возникли какие-то проблемы")})
@RequestMapping(value = "/admin/user-rest")
@Api(tags = "Пользователи", description = "Взаимодействие с пользователями")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SignUpService signUpService;


    @ApiOperation(value = "Получить всех пользователей")
    @ApiResponses({@ApiResponse(code = 200, message = "Все пользователи найдены", response = UserForm.class)})
    @GetMapping("/users")
    public ResponseEntity<List<UserForm>> getUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }


    @ApiOperation(value = "Добавить пользователя")
    @ApiResponses({@ApiResponse(code = 200, message = "Пользователь успешно добавлен", response = UserForm.class)})
    @PostMapping("/users")
    public ResponseEntity<UserForm> addUser(@ApiParam(
                    value = "Форма отправки пользователя",
                    required = true)
            @RequestBody UserForm userForm) {
        return ResponseEntity.ok(signUpService.signUp(userForm));
    }


    @ApiOperation(value = "Изменить пользователя")
    @ApiResponses({@ApiResponse(code = 200, message = "Пользователь успешно изменен", response = UserForm.class)})
    @PutMapping("users/{user-id}")
    public ResponseEntity<UserForm> updateUser(@ApiParam(
                    value = "Идентификатор пользователя",
                    required = true,
                    example = "100")
            @PathVariable("user-id") Long userId,
            @ApiParam(
                    value = "Форма отправки пользователя",
                    required = true)
            @RequestBody UserForm userForm) {
        return ResponseEntity.ok(userService.updateUser(userId, userForm));
    }


    @ApiOperation(value = "Удалить пользователя")
    @ApiResponses({@ApiResponse(code = 200, message = "Пользователь успешно удален")})
    @DeleteMapping("/users/{user-id}")
    public ResponseEntity<?> deleteUser(@ApiParam(
            value = "Идентификатор пользователя",
            required = true,
            example = "100")
            @PathVariable("user-id") Long userId) {
        System.out.println(userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
