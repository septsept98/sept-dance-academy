package com.septian.inventory.repository;

import com.septian.inventory.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, String> {

    @Query(value = "SELECT * FROM studio", nativeQuery = true)
    List<Studio> findAvailableStudios();
}
