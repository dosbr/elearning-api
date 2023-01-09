package br.com.dos.elearning.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue()
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private Long duration;
    @ManyToOne
    private Course course;
    private String description;
    @Column(name = "video_id")
    private String videoId;
}
