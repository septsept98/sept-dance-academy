package com.septian.booking.controller;

import com.septian.booking.dto.response.BookingReportPerClass;
import com.septian.booking.dto.response.BookingReportPerStudio;
import com.septian.booking.dto.request.BookingRequest;
import com.septian.booking.dto.request.StudioBookingRequest;
import com.septian.booking.entity.Booking;
import com.septian.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking create(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @PostMapping("/studio")
    public ResponseEntity<Booking> createStudioBooking(@Valid @RequestBody StudioBookingRequest request) {
        Booking booking = bookingService.createStudioBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/report/per-class")
    public List<BookingReportPerClass> getReportPerClass() {
        return bookingService.getBookingReportPerClass();
    }

    @GetMapping("/report/per-studio")
    public List<BookingReportPerStudio> getReportPerStudio() {
        return bookingService.getBookingReportPerStudio();
    }
}
