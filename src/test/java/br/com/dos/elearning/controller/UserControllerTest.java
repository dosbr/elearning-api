package br.com.dos.elearning.controller;

import br.com.dos.elearning.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void list() {


    }

    @Test
    void listById() {
    }

    @Test
    void create() {
    }
}