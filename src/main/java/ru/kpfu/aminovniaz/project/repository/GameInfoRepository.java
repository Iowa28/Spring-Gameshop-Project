package ru.kpfu.aminovniaz.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.aminovniaz.project.model.GameInfo;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {
}
