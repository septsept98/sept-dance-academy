package com.septian.booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String classId;
    @NotNull
    private Integer qty;
}
