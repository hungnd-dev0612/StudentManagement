package com.example.entities;


import com.example.dto.StudentDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;
import lombok.Data;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data

public class StudentEntity {


    private String _id;
    private String name;
    private String birthday;
    @JsonProperty("ClassEntity")
    private ClassEntity studentClass;

    public  StudentDTO convertToDTO() {
     return   JsonObject.mapFrom(this).mapTo(StudentDTO.class);
    }

}
