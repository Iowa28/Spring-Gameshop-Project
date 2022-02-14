package ru.kpfu.aminovniaz.project.service;

import ru.kpfu.aminovniaz.project.dto.UserForm;
import ru.kpfu.aminovniaz.project.model.User;

public interface SignUpService {
    public UserForm signUp(UserForm form);
}
