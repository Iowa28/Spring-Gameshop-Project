package ru.kpfu.aminovniaz.project.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsItem {
    private String id;
    private String title;
    private String link;
}
