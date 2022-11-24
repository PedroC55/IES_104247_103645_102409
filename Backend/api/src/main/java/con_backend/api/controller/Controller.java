package con_backend.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import con_backend.api.exception.ResourceNotFoundException;
import con_backend.api.model.User;
import con_backend.api.repository.UserRepository;
import con_backend.api.exception.*;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserByPassword(@PathVariable(value = "username") String userUsername)
        throws ResourceNotFoundException {
        User user = UserRepository.findByUsername(userUsernameId)
          .orElseThrow(() -> new ResourceNotFoundException("Employee not found for thiusernameid :: " userUsernameId));
        return ResponseEntity.ok().boduseree);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }
}