package com.septian.inventory.repository;

import com.septian.inventory.dto.projection.DanceClassStudioProjection;
import com.septian.inventory.entity.DanceClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DanceClassRepository extends JpaRepository<DanceClass, String> {

    @Query(value = """
            SELECT *
            FROM dance_class
            WHERE available_slot > 0
            """, nativeQuery = true)
    List<DanceClass> findAvailable();

    @Query(value = """
            SELECT *
            FROM dance_class
            WHERE available_slot >= :minSlot
            """, nativeQuery = true)
    List<DanceClass> findAvailableWithMinSlot(@Param("minSlot") int minSlot);

    @Query(value = """
        SELECT
            dc.id AS id,
            dc.name AS name,
            dc.available_slot AS availableSlot,
            dc.studio_id AS studioId,
            s.studio_name AS studioName,
            s.capacity AS capacity,
            dc.class_time AS classTime,
            dc.duration_minutes AS durationMinutes
        FROM dance_class dc
        JOIN studio s ON dc.studio_id = s.id
        WHERE (:id IS NULL OR dc.id = :id)
        ORDER BY dc.class_time
    """, nativeQuery = true)
    List<DanceClassStudioProjection> findByIdWithStudio(@Param("id") String id);

    List<DanceClass> findByStudioId(@Param("studioId") String studioId);
}
