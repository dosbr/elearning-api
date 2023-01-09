package br.com.dos.elearning.service;

import br.com.dos.elearning.domain.Course;
import br.com.dos.elearning.dto.CourseDTO;
import br.com.dos.elearning.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    public Course create(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        return courseRepository.save(course);
    }

    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course listById(UUID id) {

        Optional<Course> courseOptional = courseRepository.findById(id);

        if(courseOptional.isEmpty()){
            throw new Error();
        }

        return courseOptional.get();
    }

    public Course update(UUID id, CourseDTO courseDTO) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if(courseOptional.isEmpty()){
            throw new Error();
        }

        courseDTO.setId(id);

        Course course = modelMapper.map(courseDTO, Course.class);

        return courseRepository.save(course);
    }

    public void delete(UUID id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if(courseOptional.isEmpty()){
            throw new Error();
        }

        courseRepository.delete(courseOptional.get());
    }
}
