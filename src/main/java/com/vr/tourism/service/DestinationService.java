package com.vr.tourism.service;

import com.vr.tourism.dto.DestinationDTO;
import com.vr.tourism.entity.Destination;
import com.vr.tourism.mapper.DestinationMapper;
import com.vr.tourism.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DestinationService {
    private final DestinationRepository repo;
    private final DestinationMapper mapper;

    public List<DestinationDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    public DestinationDTO getById(String id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    public List<DestinationDTO> getByCity(String city) {
        return repo.findByCity(city).stream().map(mapper::toDTO).toList();
    }

    public List<DestinationDTO> getWith360() {
        return repo.findByHas360True().stream().map(mapper::toDTO).toList();
    }

    public List<DestinationDTO> search(String keyword) {
        return repo.findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(keyword, keyword)
                .stream().map(mapper::toDTO).toList();
    }

    public DestinationDTO save(DestinationDTO dto) {
        Destination saved = repo.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public List<String> getCities() {
        return repo.findAll().stream()
                .map(Destination::getCity)
                .distinct()
                .toList();
    }

}
