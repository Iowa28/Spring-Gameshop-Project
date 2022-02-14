package ru.kpfu.aminovniaz.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.aminovniaz.project.dto.GameForm;
import ru.kpfu.aminovniaz.project.service.GameService;
import javax.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private GameService gameService;

    private static final int gamesCountInAdminPage = 4;

    @GetMapping
    public String getAdminPage() {

        return "admin/adminMain";
    }

    @RequestMapping(value = "/addGame", method = RequestMethod.GET)
    public String getGamesPage(ModelMap map) {
        map.addAttribute("gameForm", new GameForm());
        map.addAttribute("genres", gameService.getAllGameGenre());
        return "admin/addGame";
    }

    @RequestMapping(value = "/addGame", method = RequestMethod.POST)
    public String addGame(@Valid GameForm gameForm,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            gameService.addGame(gameForm);
            redirectAttributes.addFlashAttribute("message", "Game '" + gameForm.getName() + "' has been added.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            System.out.println("Game validation error...");
            return "admin/addGame";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/gamesModify", method = RequestMethod.GET)
    public String getGamesModify(ModelMap map) {
        int pageCount = (int) Math.ceil((float)gameService.existGamesCount()/(float)gamesCountInAdminPage);
        map.addAttribute("gameForm", new GameForm());
        map.addAttribute("games", gameService.getPageGames(gamesCountInAdminPage));
        map.addAttribute("genres", gameService.getAllGameGenre());
        map.addAttribute("pageCount", pageCount);
        return "admin/gamesModify";
    }

    @RequestMapping(value = "/gamesModify/{page}", method = RequestMethod.GET)
    public String getGamesModifyPaginated(@PathVariable String page, ModelMap map) {
        int pageCount = (int) Math.ceil((float)gameService.existGamesCount()/(float)gamesCountInAdminPage);
        map.addAttribute("gameForm", new GameForm());
        map.addAttribute("games", gameService.getPageGamesPage(gamesCountInAdminPage, Integer.parseInt(page) - 1));
        map.addAttribute("genres", gameService.getAllGameGenre());
        map.addAttribute("pageCount", pageCount);
        return "admin/gamesModify";
    }

    @RequestMapping(value = "/gamesModify/{id}", method = RequestMethod.POST)
    public String modifyGame(@Valid @ModelAttribute("gameForm") GameForm gameForm, @PathVariable String id, BindingResult result) {
        gameService.updateGame(gameForm, Long.parseLong(id));
//        System.out.println(MvcUriComponentsBuilder.fromMappingName("AC#addGame").build());
        return "redirect:/admin/gamesModify";
    }

    @RequestMapping(value = "/deleteGame/{id}", method = RequestMethod.POST)
    public String deleteGame(@PathVariable String id) {
        gameService.deleteGame(Long.parseLong(id));
        return "redirect:/admin/gamesModify";
    }

    @RequestMapping(value = "/addGenre", method = RequestMethod.GET)
    public String addGenrePage() {
        return "admin/addGenre";
    }

    @RequestMapping(value = "/addGenre", method = RequestMethod.POST)
    public String addGenre(@RequestParam("name") String name) {
        gameService.addGenre(name);
        return "redirect:/admin/addGenre";
    }

}
