package ru.kpfu.aminovniaz.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @Column(name = "total_cost")
    private int totalCost;
    private Date date;

//    @PrePersist
//    public void prePersist() {
//        date = new Date();
//    }
}
