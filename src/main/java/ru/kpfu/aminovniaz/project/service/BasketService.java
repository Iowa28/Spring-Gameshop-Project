package ru.kpfu.aminovniaz.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.aminovniaz.project.aop.ExceptionLog;
import ru.kpfu.aminovniaz.project.exception.NotFoundException;
import ru.kpfu.aminovniaz.project.model.Basket;
import ru.kpfu.aminovniaz.project.model.BasketItem;
import ru.kpfu.aminovniaz.project.model.Game;
import ru.kpfu.aminovniaz.project.model.User;
import ru.kpfu.aminovniaz.project.repository.BasketItemRepository;
import ru.kpfu.aminovniaz.project.repository.BasketRepository;
import ru.kpfu.aminovniaz.project.repository.GameRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasketService {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private BasketItemRepository basketItemRepository;
    @Autowired
    private BasketRepository basketRepository;

    @ExceptionLog
    public void createBasketItem(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(NotFoundException::new);
        User user = userService.getCurrentUser();

        BasketItem basketItem = BasketItem.builder()
                .game(game)
                .user(user)
                .build();

        basketItemRepository.save(basketItem);
    }

    @ExceptionLog
    public void removeBasketItem(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(NotFoundException::new);
        User user = userService.getCurrentUser();

        basketItemRepository.removeBasketItemByGameAndUser(game, user);
    }

    public boolean isGameInBasket(Game game) {
        return basketItemRepository.existsBasketItemByGameAndUser(game, userService.getCurrentUser());
    }

    public List<Game> getGamesInBasket() {
        return getGamesInBasketOfUser(userService.getCurrentUser());
    }

    private List<Game> getGamesInBasketOfUser(User user) {
        List<Game> games = new ArrayList<>();

        List<BasketItem> items = getAllBasketItem(user);
        for (BasketItem basketItem : items) {
            Game game = basketItem.getGame();
            games.add(game);
        }

        return games;
    }

    public List<BasketItem> getAllBasketItem(User user) {
        return basketItemRepository.findAllByUser(user);
    }

    public void removeAllBasketItems(User user) {
        List<BasketItem> items = getAllBasketItem(user);
        for (BasketItem item : items) {
            basketItemRepository.removeBasketItemByGameAndUser(item.getGame(), user);
        }
    }

    public boolean makePurchase() {
        User user = userService.getCurrentUser();
        List<Game> gamesToPurchase = getGamesInBasketOfUser(user);
        int userMoney = user.getCurrentMoney();
        int totalCost = gameService.getTotalCost(gamesToPurchase);

        if (userMoney < totalCost) {
            System.out.println("You don't have enough funds sir");
            return false;
        }

        user.setCurrentMoney(userMoney - totalCost);

        Basket basket = Basket.builder()
                .totalCost(totalCost)
                .user(user)
                .date(new Date()).build();
        basketRepository.save(basket);

        for (Game game : gamesToPurchase) {
            game.getUsers().add(user);
            user.getPurchasedGames().add(game);
        }

        removeAllBasketItems(user);

        return true;
    }

    public boolean gameIsPurchased(Game someGame) {
        User user = userService.getCurrentUser();
        for (Game game : user.getPurchasedGames()) {
            if (game.equals(someGame)) {
                return true;
            }
        }

        return false;
    }


}
