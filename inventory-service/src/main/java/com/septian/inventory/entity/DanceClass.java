package com.septian.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "dance_class")
public class DanceClass {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Integer availableSlot;
    private String studioId;
    private LocalDateTime classTime;
    private Integer durationMinutes;
}
