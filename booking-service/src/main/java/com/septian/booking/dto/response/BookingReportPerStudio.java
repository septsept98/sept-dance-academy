package com.septian.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingReportPerStudio {
    private String studioId;
    private Long totalBookings;
}
