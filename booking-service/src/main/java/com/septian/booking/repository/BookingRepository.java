package com.septian.booking.repository;

import com.septian.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = """
            SELECT class_id, SUM(qty) as total
            FROM booking
            GROUP BY class_id
            """,
            nativeQuery = true)
    List<Object[]> totalBookingPerClass();

    @Query(value = """
            SELECT studio_id, COUNT(*) as total
            FROM booking
            WHERE studio_id IS NOT NULL
            GROUP BY studio_id
            """,
            nativeQuery = true)
    List<Object[]> totalBookingPerStudio();
}
