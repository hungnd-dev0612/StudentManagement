package com.example.dto;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class SpecialityDTO {
    @Setter
    private String id;
    private String name;
    private List<StudentClassDTO> studentsClass;
}
