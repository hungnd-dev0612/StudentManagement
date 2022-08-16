package com.example.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SpecialityDTO {

    private String name;
    private List<StudentClassDTO> studentsClass;


}
