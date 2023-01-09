package br.com.dos.elearning.controller;

import br.com.dos.elearning.domain.Course;
import br.com.dos.elearning.domain.Lesson;
import br.com.dos.elearning.dto.CourseDTO;
import br.com.dos.elearning.service.CourseService;
import br.com.dos.elearning.dto.LessonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<CourseDTO>> list() {
        List<Course> courses = courseService.list();
        List<CourseDTO> coursesDTOS = courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class)).toList();
        return ResponseEntity.ok().body(coursesDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> listById(@PathVariable UUID id) {
        Course course = courseService.listById(id);
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        return ResponseEntity.ok().body(courseDTO);
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity<List<LessonDTO>> listLessonsByCourseId(@PathVariable UUID id) {
        Course course = courseService.listById(id);
        List<Lesson> lessons = course.getLessons();
        List<LessonDTO> lessonDTOS = lessons
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                .toList();
        return ResponseEntity.ok().body(lessonDTOS);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
        Course courseCreated = courseService.create(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(courseCreated, CourseDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable UUID id, @RequestBody CourseDTO courseDTO) {
        Course course = courseService.update(id, courseDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(course, CourseDTO.class));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CourseDTO> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
