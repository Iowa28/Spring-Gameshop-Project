package ru.kpfu.aminovniaz.project.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.aminovniaz.project.model.Game;
import ru.kpfu.aminovniaz.project.service.BasketService;
import ru.kpfu.aminovniaz.project.service.GameService;

import javax.annotation.security.PermitAll;
import java.util.Collections;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private BasketService basketService;

    private static final int gamesCountInCatalog = 8;

    private static final int newsCount = 4;

    @PermitAll
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String homePage(ModelMap map) {
        Page<Game> pages = gameService.getPageGames(gamesCountInCatalog);
        map.addAttribute("games", pages.getContent());
        map.addAttribute("categories", gameService.getAllGameGenre());
        return "homePage";
    }

    @PermitAll
    @RequestMapping(value = "/filterCost/{cost}",method = RequestMethod.GET)
    public String searchByCost(@PathVariable String cost, ModelMap map) {
        map.addAttribute("games", gameService.searchByCost(Integer.parseInt(cost)));
        map.addAttribute("categories", gameService.getAllGameGenre());
        return "filter";
    }

    @PermitAll
    @RequestMapping(value = "/showMore", method = RequestMethod.GET)
    @ResponseBody
    public String showMore() {
        return gameService.getLastGamesResponseBody(gamesCountInCatalog);
    }

    @PermitAll
    @RequestMapping(value = "/home/filtered", method = RequestMethod.POST)
    public String homePageFiltered(@RequestParam("name") String name, ModelMap map) {
        List<Game> games = Collections.emptyList();
        if (!StringUtils.isEmpty(name) && !name.equals(" ")) {
            games = gameService.getFilteredGames(name.toUpperCase());

            map.addAttribute("header", "По запросу " + name + " найдено: " + games.size());
        }
        else {
            return "redirect:/home";
        }

        map.addAttribute("games", games);
        map.addAttribute("categories", gameService.getAllGameGenre());
        return "filter";
    }

    @PermitAll
    @RequestMapping(value = "/home/genre/{name}", method = RequestMethod.GET)
    public String getGamesByGenre(@PathVariable String name, ModelMap map) {
        List<Game> games = gameService.getGamesByGenre(name);

        map.addAttribute("games", games);
        map.addAttribute("categories", gameService.getAllGameGenre());
        return "filter";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/home/game/{id}", method = RequestMethod.GET)
    public String getGame(@PathVariable String id, ModelMap map) {
        Game someGame = gameService.getGameById(Long.parseLong(id));
        map.addAttribute("game", someGame);
        map.addAttribute("newsItems", gameService.getSteamApiNews(someGame.getGameInfo().getSteamId(), newsCount));
        map.addAttribute("isInBasket", basketService.isGameInBasket(someGame));
        map.addAttribute("isPurchased", basketService.gameIsPurchased(someGame));

        return "game";
    }
}
