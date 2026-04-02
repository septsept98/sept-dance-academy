package com.septian.booking.service;

import com.septian.booking.client.InventoryClient;
import com.septian.booking.dto.request.BookingRequest;
import com.septian.booking.dto.request.StudioBookingRequest;
import com.septian.booking.dto.response.BookingReportPerClass;
import com.septian.booking.dto.response.BookingReportPerStudio;
import com.septian.booking.dto.response.DanceClassResponse;
import com.septian.booking.entity.Booking;
import com.septian.booking.exception.ServiceUnavailableException;
import com.septian.booking.exception.SlotNotAvailableException;
import com.septian.booking.exception.StudioConflictException;
import com.septian.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import feign.FeignException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final InventoryClient inventoryClient;

    public BookingService(BookingRepository bookingRepository,
                          InventoryClient inventoryClient) {
        this.bookingRepository = bookingRepository;
        this.inventoryClient = inventoryClient;
    }

    @Transactional
    public Booking createBooking(BookingRequest request) {
        try {
            Boolean available = inventoryClient.checkAvailability(
                    request.getClassId(), request.getQty());

            if (available == null || !available) {
                throw new SlotNotAvailableException("Slot not available");
            }

            DanceClassResponse danceClass = inventoryClient.getDanceClassById(request.getClassId());
            String studioId = danceClass.getStudioId();

            if (studioId != null) {
                Boolean studioAvailable = inventoryClient.checkStudioAvailability(
                        studioId, LocalDateTime.now());
                if (studioAvailable == null || !studioAvailable) {
                    throw new StudioConflictException("Studio sedang digunakan pada waktu tersebut");
                }
            }

            inventoryClient.reduceSlot(request.getClassId(), request.getQty());

            Booking booking = new Booking();
            booking.setUserName(request.getUserName());
            booking.setClassId(request.getClassId());
            booking.setStudioId(studioId);
            booking.setQty(request.getQty());
            booking.setBookingTime(LocalDateTime.now());

            return bookingRepository.save(booking);
        } catch (SlotNotAvailableException | StudioConflictException e) {
            throw e;
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Inventory service unavailable");
        }
    }

    @Transactional
    public Booking createStudioBooking(StudioBookingRequest request) {
        try {
            Boolean available = inventoryClient.checkStudioAvailability(
                    request.getStudioId(), request.getBookingTime());

            if (available == null || !available) {
                throw new SlotNotAvailableException("Studio not available");
            }

            Booking booking = new Booking();
            booking.setUserName(request.getUserName());
            booking.setStudioId(request.getStudioId());
            booking.setBookingTime(request.getBookingTime());

            return bookingRepository.save(booking);
        } catch (SlotNotAvailableException e) {
            throw e;
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Inventory service unavailable");
        }
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> {
                    Booking b = new Booking();
                    b.setId(booking.getId());
                    b.setUserName(booking.getUserName());
                    b.setClassId(booking.getClassId());
                    b.setStudioId(booking.getStudioId());
                    b.setQty(booking.getQty());
                    b.setBookingTime(booking.getBookingTime());
                    return b;
                })
                .collect(Collectors.toList());
    }

    public List<BookingReportPerClass> getBookingReportPerClass() {
        return bookingRepository.totalBookingPerClass().stream()
                .map(row -> new BookingReportPerClass(
                        (String) row[0],
                        ((Number) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<BookingReportPerStudio> getBookingReportPerStudio() {
        return bookingRepository.totalBookingPerStudio().stream()
                .map(row -> new BookingReportPerStudio(
                        (String) row[0],
                        ((Number) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}
