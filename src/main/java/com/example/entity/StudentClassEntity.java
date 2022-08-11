package com.example.entity;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class StudentClassEntity {
    @Setter
    private String _id;
    private String name;
    private SpecialityEntity major;
    private List<StudentEntity> students;

}
