package br.com.dos.elearning.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue()
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private String image;
    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;
}
