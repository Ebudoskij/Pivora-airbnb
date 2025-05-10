package org.ebudoskyi.houserent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "message_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id",  nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id",  nullable = false)
    private User toUser;

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
