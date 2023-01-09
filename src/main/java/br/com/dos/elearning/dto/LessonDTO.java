package br.com.dos.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private UUID id;
    private String name;
    private String image;
    private UUID courseId;
    private String description;
    private String videoId;
}
