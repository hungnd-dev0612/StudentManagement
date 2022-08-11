package com.example.dto;

import lombok.Data;
import lombok.Setter;

@Data

public class StudentDTO {
    @Setter
    private String id;
    private String name;
    private String birthday;
    private StudentClassDTO studentClass;

}
