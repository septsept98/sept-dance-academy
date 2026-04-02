package com.septian.inventory.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanceClassRequest {
    private String id;
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Studio must not be empty")
    private String studioId;

    @NotNull(message = "Class time must not be null")
    @Future(message = "Class time must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime classTime;

    @NotNull(message = "Duration must not be null")
    @Min(value = 90, message = "Minimum duration is 90 minutes")
    private Integer durationMinutes;

    private Integer availableSlot;
}
