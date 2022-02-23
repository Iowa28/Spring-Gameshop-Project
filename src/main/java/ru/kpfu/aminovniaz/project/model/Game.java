package ru.kpfu.aminovniaz.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Lob
    private String annotation;
    private String cover;
    private int cost;
    @Column(name = "deleted")
    private boolean deleted;

    @ManyToMany(mappedBy = "purchasedGames", fetch = FetchType.EAGER)
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    private GameGenre gameGenre;

    @OneToOne(fetch = FetchType.LAZY)
    private GameInfo gameInfo;
}
