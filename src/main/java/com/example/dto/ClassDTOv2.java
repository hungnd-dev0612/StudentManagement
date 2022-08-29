package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClassDTOv2 {
    private String classId;
    private String className;
    private String specialitiesName;
    private String specialitiesId;
}
