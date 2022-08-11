package com.example.dto;


import com.example.entity.SpecialityEntity;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class StudentClassDTO {
    @Setter
    private String id;
    private String name;
    private SpecialityEntity major;
    private List<StudentDTO> students;
}
