package com.septian.inventory.service;

import com.septian.inventory.dto.request.DanceClassRequest;
import com.septian.inventory.dto.response.DanceClassResponse;
import com.septian.inventory.dto.response.ResponseMessage;
import com.septian.inventory.entity.DanceClass;
import com.septian.inventory.entity.Studio;
import com.septian.inventory.exception.InsufficientSlotException;
import com.septian.inventory.exception.ResourceNotFoundException;
import com.septian.inventory.repository.DanceClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DanceClassService {

    private final DanceClassRepository danceClassRepository;
    private final StudioService studioService;

    public DanceClassService(DanceClassRepository danceClassRepository, StudioService studioService) {
        this.danceClassRepository = danceClassRepository;
        this.studioService = studioService;
    }

    @Transactional
    public ResponseMessage createDanceClass(DanceClassRequest danceClassRequest) {
        Studio studio = studioService.getStudioById(danceClassRequest.getStudioId());

        DanceClass danceClass = new DanceClass();
        danceClass.setName(danceClassRequest.getName());
        danceClass.setStudioId(danceClassRequest.getStudioId());
        danceClass.setAvailableSlot(studio.getCapacity());
        danceClass.setClassTime(danceClassRequest.getClassTime());
        danceClass.setDurationMinutes(danceClassRequest.getDurationMinutes());
        danceClassRepository.save(danceClass);
        return new ResponseMessage(201, "Success");
    }

    @Transactional
    public ResponseMessage updateDanceClass(String id, DanceClassRequest updated) {
        DanceClass existing = getDanceClassById(id);
        existing.setName(updated.getName());
        existing.setAvailableSlot(updated.getAvailableSlot());
        existing.setClassTime(updated.getClassTime());
        existing.setDurationMinutes(updated.getDurationMinutes());

        Studio studio = studioService.getStudioById(updated.getStudioId());
        existing.setStudioId(studio.getId());
        danceClassRepository.save(existing);
        return new  ResponseMessage(200,"Success");
    }

    public List<DanceClassResponse> getAllDanceClasses() {
        return danceClassRepository.findByIdWithStudio(null)
                .stream()
                .map(dc -> {
                    DanceClassResponse result = new DanceClassResponse();
                    result.setId(dc.getId());
                    result.setName(dc.getName());
                    result.setAvailableSlot(dc.getAvailableSlot());
                    result.setDurationMinutes(dc.getDurationMinutes());
                    result.setClassTime(dc.getClassTime());
                    result.setStudioId(dc.getStudioId());
                    result.setStudioName(dc.getStudioName());
                    result.setCapacity(dc.getCapacity());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public DanceClass getDanceClassById(String id) {
        return danceClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dance class not found"));
    }

    public ResponseMessage deleteDanceClass(String id) {
        getDanceClassById(id);
        danceClassRepository.deleteById(id);
        return new ResponseMessage(200, "Success");
    }


    // ---- Slot Management ----

    public boolean checkAvailability(String classId, int qty) {
        DanceClass danceClass = getDanceClassById(classId);
        return danceClass.getAvailableSlot() >= qty;
    }

    public DanceClass reduceSlot(String classId, int qty) {
        DanceClass danceClass = getDanceClassById(classId);
        if (danceClass.getAvailableSlot() < qty) {
            throw new InsufficientSlotException("Insufficient available slots");
        }
        danceClass.setAvailableSlot(danceClass.getAvailableSlot() - qty);
        return danceClassRepository.save(danceClass);
    }

    public List<DanceClass> getAvailableDanceClasses() {
        return danceClassRepository.findAvailable();
    }

    public List<DanceClass> getAvailableDanceClassesWithMinSlot(int minSlot) {
        return danceClassRepository.findAvailableWithMinSlot(minSlot);
    }

}
