package com.septian.booking.client;

import com.septian.booking.dto.response.DanceClassResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {

    @GetMapping("/api/dance-classes/{classId}/check-availability")
    Boolean checkAvailability(@PathVariable("classId") String classId, @RequestParam("qty") int qty);

    @PutMapping("/api/dance-classes/{classId}/reduce-slot")
    void reduceSlot(@PathVariable("classId") String classId, @RequestParam("qty") int qty);

    @GetMapping("/api/studios/{studioId}/check-availability")
    Boolean checkStudioAvailability(@PathVariable("studioId") String studioId, @RequestParam("time") LocalDateTime time);

    @GetMapping("/api/dance-classes/{classId}")
    DanceClassResponse getDanceClassById(@PathVariable("classId") String classId);
}
