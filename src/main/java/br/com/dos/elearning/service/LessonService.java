package br.com.dos.elearning.service;

import br.com.dos.elearning.domain.Lesson;
import br.com.dos.elearning.dto.LessonDTO;
import br.com.dos.elearning.repository.LessonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    ModelMapper modelMapper;

    public Lesson create(LessonDTO lessonDTO) {
        Lesson lesson = modelMapper.map(lessonDTO, Lesson.class);
        return lessonRepository.save(lesson);
    }

    public List<Lesson> list() {
        return lessonRepository.findAll();
    }

    public Lesson listById(UUID id) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);

        if(lessonOptional.isEmpty()){
            throw new Error();
        }

        return lessonOptional.get();
    }

    public Lesson update(UUID id, LessonDTO lessonDTO) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);

        if(lessonOptional.isEmpty()){
            throw new Error();
        }

        lessonDTO.setId(id);

        Lesson lesson = modelMapper.map(lessonDTO, Lesson.class);

        return lessonRepository.save(lesson);
    }

    public void delete(UUID id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);

        if(lessonOptional.isEmpty()){
            throw new Error();
        }

        lessonRepository.delete(lessonOptional.get());
    }
}
