package com.septian.inventory.controller;

import com.septian.inventory.dto.request.DanceClassRequest;
import com.septian.inventory.dto.response.DanceClassResponse;
import com.septian.inventory.dto.response.ResponseMessage;
import com.septian.inventory.entity.DanceClass;
import com.septian.inventory.service.DanceClassService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DanceClassController {

    private final DanceClassService danceClassService;

    public DanceClassController(DanceClassService danceClassService) {
        this.danceClassService = danceClassService;
    }

    @PostMapping("/api/dance-classes")
    public ResponseEntity<ResponseMessage> createDanceClass(@RequestBody @Valid DanceClassRequest danceClass) {
        return ResponseEntity.status(HttpStatus.CREATED).body(danceClassService.createDanceClass(danceClass));
    }

    @GetMapping("/api/dance-classes")
    public ResponseEntity<List<DanceClassResponse>> getAllDanceClasses() {
        return ResponseEntity.ok(danceClassService.getAllDanceClasses());
    }

    @GetMapping("/api/dance-classes/{id}")
    public ResponseEntity<DanceClass> getDanceClassById(@PathVariable String id) {
        return ResponseEntity.ok(danceClassService.getDanceClassById(id));
    }

    @PutMapping("/api/dance-classes/{id}")
    public ResponseEntity<ResponseMessage> updateDanceClass(@PathVariable String id, @RequestBody @Valid DanceClassRequest danceClass) {
        return ResponseEntity.ok(danceClassService.updateDanceClass(id, danceClass));
    }

    @DeleteMapping("/api/dance-classes/{id}")
    public ResponseEntity<ResponseMessage> deleteDanceClass(@PathVariable String id) {
        return ResponseEntity.ok(danceClassService.deleteDanceClass(id));
    }

    // ---- DanceClass Availability ----

    @GetMapping("/api/dance-classes/available")
    public ResponseEntity<List<DanceClass>> getAvailableDanceClasses(
            @RequestParam(required = false) Integer minSlot) {
        if (minSlot != null) {
            return ResponseEntity.ok(danceClassService.getAvailableDanceClassesWithMinSlot(minSlot));
        }
        return ResponseEntity.ok(danceClassService.getAvailableDanceClasses());
    }

    @GetMapping("/api/dance-classes/{id}/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable String id, @RequestParam int qty) {
        return ResponseEntity.ok(danceClassService.checkAvailability(id, qty));
    }

    @PutMapping("/api/dance-classes/{id}/reduce-slot")
    public ResponseEntity<DanceClass> reduceSlot(@PathVariable String id, @RequestParam int qty) {
        return ResponseEntity.ok(danceClassService.reduceSlot(id, qty));
    }
}
