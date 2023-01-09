package br.com.dos.elearning.controller;

import br.com.dos.elearning.domain.Lesson;
import br.com.dos.elearning.dto.LessonDTO;
import br.com.dos.elearning.service.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("lessons")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<LessonDTO>> list() {
        List<Lesson> lessons = lessonService.list();
        List<LessonDTO> lessonDTOS = lessons.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class)).toList();
        return ResponseEntity.ok().body(lessonDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> listById(@PathVariable UUID id) {
        Lesson lesson = lessonService.listById(id);
        LessonDTO lessonDTO = modelMapper.map(lesson, LessonDTO.class);
        return ResponseEntity.ok().body(lessonDTO);
    }


    @PostMapping
    public ResponseEntity<LessonDTO> create(@RequestBody LessonDTO lessonDTO) {
        Lesson lessonCreated = lessonService.create(lessonDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(lessonCreated, LessonDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> update(@PathVariable UUID id, @RequestBody LessonDTO lessonDTO) {
        Lesson lesson = lessonService.update(id, lessonDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(lesson, LessonDTO.class));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<LessonDTO> delete(@PathVariable UUID id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
