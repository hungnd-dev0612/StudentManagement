package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StudentDTO {

    @JsonProperty("_id")
    private String id;
    private String name;
    private String birthday;
    private StudentClassDTO studentClass;

}
