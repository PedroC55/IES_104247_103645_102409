package con_backend.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

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

import con_backend.api.model.Device;
//import con_backend.api.exception.ResourceNotFoundException;
import con_backend.api.model.User;
import con_backend.api.repository.UserRepository;
import con_backend.api.repository.DeviceRepository;
import con_backend.api.exception.*;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/users?username={username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String userUsername)
        throws ResourceNotFoundException {
            User user = userRepository.findByUsername(userUsername);
            return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
        throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
         throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
       .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }



    @GetMapping("/devices?name={name}")
    public ResponseEntity<Device> getDeviceByName(@PathVariable(value = "name") String name)
        throws ResourceNotFoundException {
            Device device = deviceRepository.findByName(name);
            return ResponseEntity.ok().body(device);
    }


    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") Long deviceId)
        throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
          .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));
        return ResponseEntity.ok().body(device);
    }

    @PostMapping("/devices")
    public Device createDevice(@Valid @RequestBody Device device) {
        return deviceRepository.save(device);
    }

    @DeleteMapping("/diveces/{id}")
    public Map<String, Boolean> deleteDivece(@PathVariable(value = "id") Long deviceId)
         throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
       .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));

        deviceRepository.delete(device);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}