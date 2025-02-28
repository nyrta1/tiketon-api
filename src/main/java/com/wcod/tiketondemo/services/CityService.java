package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.CityRequestDTO;
import com.wcod.tiketondemo.data.models.City;
import com.wcod.tiketondemo.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    //@Cacheable(value = "cities")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    //@CacheEvict(value = "cities", allEntries = true)
    public City saveCity(CityRequestDTO cityRequestDTO) {
        City city = modelMapper.map(cityRequestDTO, City.class);
        return cityRepository.saveAndFlush(city);
    }
}
