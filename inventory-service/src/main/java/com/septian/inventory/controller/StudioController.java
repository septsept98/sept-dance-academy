package com.septian.inventory.controller;

import com.septian.inventory.entity.Studio;
import com.septian.inventory.service.StudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StudioController {

    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    // ---- Studio CRUD ----

    @PostMapping("/api/studios")
    public ResponseEntity<Studio> createStudio(@RequestBody Studio studio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studioService.createStudio(studio));
    }

    @GetMapping("/api/studios")
    public ResponseEntity<List<Studio>> getAllStudios() {
        return ResponseEntity.ok(studioService.getAllStudios());
    }

    @GetMapping("/api/studios/{id}")
    public ResponseEntity<Studio> getStudioById(@PathVariable String id) {
        return ResponseEntity.ok(studioService.getStudioById(id));
    }

    @PutMapping("/api/studios/{id}")
    public ResponseEntity<Studio> updateStudio(@PathVariable String id, @RequestBody Studio studio) {
        return ResponseEntity.ok(studioService.updateStudio(id, studio));
    }

    @DeleteMapping("/api/studios/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable String id) {
        studioService.deleteStudio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/studios/{id}/check-availability")
    public ResponseEntity<Boolean> checkStudioAvailability(
            @PathVariable String id, @RequestParam LocalDateTime time) {
        return ResponseEntity.ok(studioService.checkStudioAvailability(id, time));
    }
}
