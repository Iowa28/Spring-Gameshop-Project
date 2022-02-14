package ru.kpfu.aminovniaz.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kpfu.aminovniaz.project.aop.ExceptionLog;
import ru.kpfu.aminovniaz.project.dto.UserForm;
import ru.kpfu.aminovniaz.project.exception.NotFoundException;
import ru.kpfu.aminovniaz.project.model.User;
import ru.kpfu.aminovniaz.project.model.UserDetailsImpl;
import ru.kpfu.aminovniaz.project.repository.UserRepository;

import java.util.List;

import static ru.kpfu.aminovniaz.project.dto.UserForm.from;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @ExceptionLog
    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким именем не существует."));
        return new UserDetailsImpl(user);
    }

    @ExceptionLog
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Пользователь с таким именем не существует."));

        return currentUser;
    }

    public List<UserForm> getAllUser() {
        return from(userRepo.findAll());
    }

    @ExceptionLog
    public UserForm updateUser(Long userId, UserForm userForm) {
        User userForUpdate = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким идентификатором не существует."));

        userForUpdate.setUsername(userForm.getUsername());
        userForUpdate.setEmail(userForm.getEmail());
        userForUpdate.setCurrentMoney(userForm.getMoney());
        userRepo.save(userForUpdate);

        return from(userForUpdate);
    }

    @ExceptionLog
    public void deleteUser(Long userId) {
        User userForDelete = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким идентификатором не существует."));

        userRepo.delete(userForDelete);
    }
}
