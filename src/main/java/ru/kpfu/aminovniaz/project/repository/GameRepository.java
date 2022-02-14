package ru.kpfu.aminovniaz.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.aminovniaz.project.model.Game;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game>{
    Optional<Game> findByName(String name);

    Optional<Game> findById(Long id);

    @Query("SELECT g FROM Game g WHERE g.name LIKE %:name% ORDER BY g.cost")
    List<Game> searchByNameStartWith(@Param("name") String name);

    @Query("SELECT g FROM Game g WHERE g.cost < :cost ORDER BY g.cost ASC")
    List<Game> searchByCostLess(@Param("cost") int cost);

    List<Game> findAllByDeletedFalseOrderByIdDesc();

    List<Game>  findAllByDeletedFalse();
}
