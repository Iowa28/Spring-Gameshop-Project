package ru.kpfu.aminovniaz.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kpfu.aminovniaz.project.aop.ExceptionLog;
import ru.kpfu.aminovniaz.project.dto.UserForm;
import ru.kpfu.aminovniaz.project.model.Game;
import ru.kpfu.aminovniaz.project.model.User;
import ru.kpfu.aminovniaz.project.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static int START_MONEY = 5000;

    @Override
    public UserForm signUp(UserForm form) {
        User user = User.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .currentMoney(START_MONEY)
                .role(User.Role.ADMIN).build();

        userRepository.save(user);
        return form;
    }


}
