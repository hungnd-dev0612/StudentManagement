package com.example.services;

import com.example.dto.SpecialityDTO;
import io.vertx.core.Future;

import java.util.List;

public interface SpecialityService {

    Future<List<SpecialityDTO>> getAll();

    Future<SpecialityDTO> insert(SpecialityDTO dto);

    Future<SpecialityDTO> update(String id, SpecialityDTO dto);

    Future<Boolean> delete(String id);

    Future<SpecialityDTO> findById(String id);

    Future<SpecialityDTO> findByName(String name);
}
