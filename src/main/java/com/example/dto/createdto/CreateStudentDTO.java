package com.example.dto.createdto;

import com.example.dto.ClassDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStudentDTO {
    private String name;
    private String birthday;
    private ClassDTO studentClass;
}
