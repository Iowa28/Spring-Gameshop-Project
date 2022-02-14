package ru.kpfu.aminovniaz.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kpfu.aminovniaz.project.dto.UserForm;
import ru.kpfu.aminovniaz.project.service.SignUpService;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@Controller
public class SecurityController {

    @Autowired
    private SignUpService signUpService;

    @PermitAll
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String login() {
        return "sign_in";
    }

    @PermitAll
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String getSignUp(ModelMap map) {
        map.addAttribute("userForm", new UserForm());
        return "sign_up";
    }

    @PermitAll
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@Valid UserForm userForm, BindingResult result) {
        if (!result.hasErrors()) {
            signUpService.signUp(userForm);
        } else {
            System.out.println("User validation error...");
            return "sign_up";
        }
        return "redirect:/signIn";
    }

}
