package br.com.dos.elearning.service;

import br.com.dos.elearning.domain.User;
import br.com.dos.elearning.dto.UserDTO;
import br.com.dos.elearning.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public User create(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User listById(UUID id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new Error();
        }

        return userOptional.get();
    }

    public User update(UUID id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new Error();
        }

        userDTO.setId(id);

        User user = modelMapper.map(userDTO, User.class);

        return userRepository.save(user);
    }

    public void delete(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new Error();
        }

        userRepository.delete(userOptional.get());
    }

    public User loadUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Username not found!");
        }
        return optionalUser.get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        } else {
            User user = userOptional.get();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRouleList().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

}
