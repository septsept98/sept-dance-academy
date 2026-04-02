package com.septian.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanceClassResponse {
    private String id;
    private String name;
    private Integer availableSlot;
    private String studioId;
}
