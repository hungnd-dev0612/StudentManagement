package com.example.services;

import com.example.dto.SpecialityDTO;
import io.vertx.core.Future;

import java.util.List;

public interface SpecialityService {
    Future<List<SpecialityDTO>> getAll();

    Future<SpecialityDTO> findById(String id);

    Future<SpecialityDTO> insert(SpecialityDTO dto);

    Future<SpecialityDTO> update(String id, SpecialityDTO dto);

    void delete(String id);
}
