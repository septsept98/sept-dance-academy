package com.septian.inventory.dto.projection;

import java.time.LocalDateTime;

public interface DanceClassStudioProjection {
    String getId();
    String getName();
    Integer getAvailableSlot();
    String getStudioId();
    String getStudioName();
    Integer getCapacity();
    LocalDateTime getClassTime();
    Integer getDurationMinutes();
}
