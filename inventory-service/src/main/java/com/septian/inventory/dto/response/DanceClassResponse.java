package com.septian.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanceClassResponse {
    private String id;
    private String name;
    private Integer availableSlot;
    private String studioId;
    private String studioName;
    private Integer capacity;
    private LocalDateTime classTime;
    private Integer durationMinutes;
}
