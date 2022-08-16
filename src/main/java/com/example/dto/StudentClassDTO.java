package com.example.dto;


import com.example.entities.SpecialityEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StudentClassDTO {
    private String name;
    private SpecialityEntity major;
    private List<StudentDTO> students;
}
