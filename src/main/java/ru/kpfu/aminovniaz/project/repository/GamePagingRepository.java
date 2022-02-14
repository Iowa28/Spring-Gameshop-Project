package ru.kpfu.aminovniaz.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.aminovniaz.project.model.Game;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface GamePagingRepository extends PagingAndSortingRepository<Game, Long> {

    @Query(value = "SELECT * FROM game AS g WHERE deleted = false ORDER BY g.id", nativeQuery = true)
    Page<Game> findAllByPagination(Pageable pageable);

    @Query(value = "SELECT * FROM game AS g WHERE deleted = false", nativeQuery = true)
    Page<Game> findAllByPaginationNotOrdered(Pageable pageable);
}
