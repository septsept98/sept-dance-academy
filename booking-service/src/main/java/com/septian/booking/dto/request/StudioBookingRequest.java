package com.septian.booking.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudioBookingRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String studioId;
    @NotNull(message = "Booking time must not be null")
    @Future(message = "Booking time must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingTime;
}
