package org.ebudoskyi.houserent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String content;
    private LocalDateTime timestamp;
}
