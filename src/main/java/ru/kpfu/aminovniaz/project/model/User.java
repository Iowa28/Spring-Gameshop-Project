package ru.kpfu.aminovniaz.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(name = "current_money")
    private int currentMoney;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_games",
            joinColumns = {@JoinColumn(name = "usr_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "id")})
    private List<Game> purchasedGames;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN
    }
}
