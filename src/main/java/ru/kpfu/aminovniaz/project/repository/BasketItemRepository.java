package ru.kpfu.aminovniaz.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.aminovniaz.project.model.BasketItem;
import ru.kpfu.aminovniaz.project.model.Game;
import ru.kpfu.aminovniaz.project.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    List<BasketItem> findAllByUser(User user);

    boolean existsBasketItemByGameAndUser(Game game, User user);

    void removeBasketItemByGameAndUser(Game game, User user);
}
