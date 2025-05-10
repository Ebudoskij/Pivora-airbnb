package org.ebudoskyi.houserent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "review_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",  nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id",  nullable = false)
    private Property property;

    @NotNull
    @Min(value = 1, message = "rating shouldn`t be lower 1")
    @Max(value = 5, message = "Rating shouldn`t exceed 5")
    private int rating;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;
}
