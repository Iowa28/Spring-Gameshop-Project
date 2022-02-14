package ru.kpfu.aminovniaz.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.aminovniaz.project.model.GameGenre;

import java.util.Optional;

@Repository
public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {
    Optional<GameGenre> findByName(String name);

    Optional<GameGenre> findById(Long id);
}
