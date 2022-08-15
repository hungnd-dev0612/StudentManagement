package com.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassEntity {
    private String _id;
    private String name;
    private SpecialityEntity speciality;
    @JsonProperty("StudentEntity")
    private List<StudentEntity> students;

}
