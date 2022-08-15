package com.example.dto;


import com.example.entities.SpecialityEntity;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
public class StudentClassDTO {
    private String name;
    private SpecialityEntity major;
    private List<StudentDTO> students;
}
