package com.septian.inventory.service;

import com.septian.inventory.entity.DanceClass;
import com.septian.inventory.entity.Studio;
import com.septian.inventory.exception.InsufficientSlotException;
import com.septian.inventory.exception.ResourceNotFoundException;
import com.septian.inventory.repository.DanceClassRepository;
import com.septian.inventory.repository.StudioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioService {

    private final StudioRepository studioRepository;
    private final DanceClassRepository danceClassRepository;

    public StudioService(StudioRepository studioRepository, DanceClassRepository danceClassRepository) {
        this.studioRepository = studioRepository;
        this.danceClassRepository = danceClassRepository;
    }

    // ---- Studio CRUD ----

    public Studio createStudio(Studio studio) {
        return studioRepository.save(studio);
    }

    public List<Studio> getAllStudios() {
        return studioRepository.findAll()
                .stream()
                .map(s -> {
                    Studio result = new Studio();
                    result.setId(s.getId());
                    result.setStudioName(s.getStudioName());
                    result.setLocation(s.getLocation());
                    result.setCapacity(s.getCapacity());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public Studio getStudioById(String id) {
        return studioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studio not found"));
    }

    public Studio updateStudio(String id, Studio updated) {
        Studio existing = getStudioById(id);
        existing.setStudioName(updated.getStudioName());
        existing.setLocation(updated.getLocation());
        existing.setCapacity(updated.getCapacity());
        return studioRepository.save(existing);
    }

    public void deleteStudio(String id) {
        List<DanceClass> classList = danceClassRepository.findByStudioId(id);
        if (classList.isEmpty())
            studioRepository.deleteById(id);
        else
            throw new InsufficientSlotException("Studio still used!");
    }

    public boolean checkStudioAvailability(String studioId, LocalDateTime time) {
        getStudioById(studioId);
        return true;
    }
}
