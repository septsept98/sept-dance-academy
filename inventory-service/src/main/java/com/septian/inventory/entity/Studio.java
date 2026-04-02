package com.septian.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "studio")
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String studioName;
    private String location;
    private Integer capacity;
}
