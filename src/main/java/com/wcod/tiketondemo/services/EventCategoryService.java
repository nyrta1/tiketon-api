package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.EventCategoryRequestDTO;
import com.wcod.tiketondemo.data.models.EventCategory;
import com.wcod.tiketondemo.repository.EventCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventCategoryService {
    private final EventCategoryRepository eventCategoryRepository;
    private final ModelMapper modelMapper;

    //@Cacheable(value = "eventCategories")
    public List<EventCategory> getAllCategories() {
        return eventCategoryRepository.findAll();
    }

    //@CacheEvict(value = "eventCategories", allEntries = true)
    public EventCategory createCategory(EventCategoryRequestDTO eventCategoryRequestDTO) {
        EventCategory category = modelMapper.map(eventCategoryRequestDTO, EventCategory.class);
        return eventCategoryRepository.saveAndFlush(category);
    }
}
