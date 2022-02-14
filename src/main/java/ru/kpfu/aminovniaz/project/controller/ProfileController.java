package ru.kpfu.aminovniaz.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kpfu.aminovniaz.project.model.Game;
import ru.kpfu.aminovniaz.project.model.User;
import ru.kpfu.aminovniaz.project.model.UserDetailsImpl;
import ru.kpfu.aminovniaz.project.service.BasketService;
import ru.kpfu.aminovniaz.project.service.GameService;
import ru.kpfu.aminovniaz.project.service.UserService;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private BasketService basketService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    private boolean purchaseSuccessful = true;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfilePage(ModelMap map) {
        User user = userService.getCurrentUser();
        List<Game> games = gameService.filterGames(user.getPurchasedGames());

        map.addAttribute("user", user);
        map.addAttribute("games", games);

        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String getBasketPage(ModelMap map) {
        List<Game> games = basketService.getGamesInBasket();
        games = gameService.filterGames(games);
        map.addAttribute("games", games);
        map.addAttribute("totalCost", gameService.getTotalCost(games));
        map.addAttribute("basketIsEmpty", games.isEmpty());
        map.addAttribute("purchaseSuccessful", purchaseSuccessful);

        return "basket";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/basket/add/{id}", method = RequestMethod.GET)
    public String addGameToBasket(@PathVariable String id) {
        basketService.createBasketItem(Long.parseLong(id));

        return "redirect:/home/game/{id}";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/basket/remove/{id}", method = RequestMethod.GET)
    public String removeGameFromBasket(@PathVariable String id) {
        basketService.removeBasketItem(Long.parseLong(id));

        return "redirect:/basket";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/basket/makePurchase", method = RequestMethod.GET)
    public String makePurchase() {
        purchaseSuccessful = basketService.makePurchase();

        if (!purchaseSuccessful) {
            return "redirect:/basket";
        }

        return "redirect:/profile";
    }
}
