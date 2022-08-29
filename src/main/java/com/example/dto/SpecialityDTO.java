package com.example.dto;


import com.example.entities.ClassEntity;
import com.example.entities.SpecialityEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SpecialityDTO {
    @JsonProperty("_id")
    private String id;
    private String name;

    public static SpecialityDTO convertToDTO(SpecialityEntity entity) {
        return JsonObject.mapFrom(entity).mapTo(SpecialityDTO.class);
    }

    public static SpecialityEntity convertToEntity(SpecialityDTO dto) {
        return JsonObject.mapFrom(dto).mapTo(SpecialityEntity.class);
    }

}
