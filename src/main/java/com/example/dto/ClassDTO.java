package com.example.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClassDTO {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String specialitiesId;
}