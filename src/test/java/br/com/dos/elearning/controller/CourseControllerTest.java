package br.com.dos.elearning.controller;

import br.com.dos.elearning.domain.Course;
import br.com.dos.elearning.domain.Lesson;
import br.com.dos.elearning.dto.CourseDTO;
import br.com.dos.elearning.service.CourseService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CourseService courseService;

    @MockBean
    ModelMapper modelMapper;


    @Test
    @DisplayName("Should to be able return a course list")
    void list() throws Exception {
      Course course = new Course(
              UUID.randomUUID(),
              "Ciência da Computação",
              "https://www.tuiuti.edu.br/hubfs/ci%C3%AAncia%20da%20computa%C3%A7%C3%A3o%202.jpg",
              List.of(new Lesson()));

      CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName(), 1L);

      Mockito.when(courseService.list()).thenReturn(List.of(course));

      Mockito.when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);

      Map<String, Object> courseMap = new HashMap<>();
        courseMap.put("id", courseDTO.getId().toString());
        courseMap.put("name", courseDTO.getName());
        courseMap.put("duration", courseDTO.getDuration());

      this.mvc.perform(get("/courses"))
              .andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().json(List.of(new JSONObject(courseMap)).toString()));

    }

    @Test
    void listById() {
        List mockedList = Mockito.mock(List.class);

        mockedList.add("One");
        mockedList.clear();

        Mockito.verify(mockedList);
    }

    @Test
    void listLessonsByCourseId() {
    }

    @Test
    @DisplayName("Should to be able create a new course")
    void create() throws Exception {
        Course course = new Course(
                UUID.randomUUID(),
                "Ciência da Computação",
                "https://www.tuiuti.edu.br/hubfs/ci%C3%AAncia%20da%20computa%C3%A7%C3%A3o%202.jpg",
                List.of(new Lesson()));

        CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName(), 1L);

        Mockito.when(courseService.create(any())).thenReturn(course);

        Mockito.when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);

        Map<String, Object> newCourseMapRequest = new HashMap<>();

        newCourseMapRequest.put("name", courseDTO.getName());
        newCourseMapRequest.put("duration", courseDTO.getDuration());

        Map<String, Object> newCourseMapResponse = new HashMap<>();

        newCourseMapResponse.put("id", courseDTO.getId().toString());
        newCourseMapResponse.put("name", courseDTO.getName());
        newCourseMapResponse.put("duration", courseDTO.getDuration());

        this.mvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(newCourseMapRequest).toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(new JSONObject(newCourseMapResponse).toString()));
    }

    @Test
    @DisplayName("Should to be able update a course")
    void update() throws Exception {
        Course newCourse = new Course(
                UUID.randomUUID(),
                "Ciência da Computação",
                "https://www.tuiuti.edu.br/hubfs/ci%C3%AAncia%20da%20computa%C3%A7%C3%A3o%202.jpg",
                List.of(new Lesson()));

        CourseDTO courseDTO = new CourseDTO(newCourse.getId(), newCourse.getName(), 1L);

        Mockito.when(courseService.create(any())).thenReturn(newCourse);
        Mockito.when(modelMapper.map(any(), any())).thenReturn(courseDTO);

        Map<String, Object> newCourseMapRequest = new HashMap<>();

        newCourseMapRequest.put("name", courseDTO.getName());
        newCourseMapRequest.put("duration", courseDTO.getDuration());

        this.mvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(newCourseMapRequest).toString())
                .accept(MediaType.APPLICATION_JSON));

        newCourseMapRequest.replace("name", "Sistema de informação");

        Map<String, Object> newCourseMapResponse = new HashMap<>();

        newCourseMapResponse.put("id", courseDTO.getId().toString());
        newCourseMapResponse.put("name", courseDTO.getName());
        newCourseMapResponse.put("duration", courseDTO.getDuration());

        URI uri = new URI("/courses/" + courseDTO.getId().toString());

        this.mvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject(newCourseMapRequest).toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new JSONObject(newCourseMapResponse).toString()));


    }

    @Test
    void delete() {

    }
}