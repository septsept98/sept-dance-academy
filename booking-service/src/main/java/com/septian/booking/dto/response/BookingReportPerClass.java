package com.septian.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingReportPerClass {
    private String classId;
    private Long totalQty;
}
