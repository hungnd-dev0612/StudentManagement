package com.example.entity;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class SpecialityEntity {
    @Setter
    private String _id;
    private String name;
    private List<StudentClassEntity> studentsClass;
}
