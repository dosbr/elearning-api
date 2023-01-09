package br.com.dos.elearning.controller;

import br.com.dos.elearning.domain.User;
import br.com.dos.elearning.dto.UserDTO;
import br.com.dos.elearning.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        List<User> users = userService.list();
        List<UserDTO> usersDTO = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class)).toList();
        return ResponseEntity.ok().body(usersDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> listById(@PathVariable UUID id) {
        User user = userService.listById(id);
        UserDTO usersDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(usersDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        User userCreated = userService.create(userDTO);
        UserDTO usersDTO = modelMapper.map(userCreated, UserDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(usersDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        User userCreated = userService.update(id, userDTO);
        UserDTO usersDTO = modelMapper.map(userCreated, UserDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
