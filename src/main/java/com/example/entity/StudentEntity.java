package com.example.entity;


import com.example.dto.StudentDTO;
import io.vertx.core.json.JsonObject;
import lombok.Data;
import lombok.Setter;

@Data
public class StudentEntity {

    @Setter
    private String _id;
    private String name;
    private String birthday;
    private StudentClassEntity studentClass;

//    public StudentDTO convertToDTO(StudentEntity entity) {
//        StudentDTO dto = new StudentDTO();
//        JsonObject parseObject = new JsonObject();
//        dto = parseObject.mapTo(entity);
//        return dto;
//    }

}
