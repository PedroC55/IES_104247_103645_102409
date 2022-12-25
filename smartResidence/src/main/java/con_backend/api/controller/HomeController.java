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
import org.springframework.stereotype.Controller;

import con_backend.api.model.Device;
//import con_backend.api.exception.ResourceNotFoundException;
import con_backend.api.model.User;
import con_backend.api.model.UserDevice;
import con_backend.api.model.Vacuum;

import con_backend.api.repository.UserRepository;
import con_backend.api.repository.UserDeviceRepository;
import con_backend.api.repository.DeviceRepository;
import con_backend.api.repository.VacuumRepository;

import con_backend.api.exception.*;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private VacuumRepository vacuumRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping("api/listUserDevices/{userId}")
    public ResponseEntity<List<UserDevice>> getDevicesByUserId(@PathVariable(value = "userId") Long userId)
        throws ResourceNotFoundException {
        List<UserDevice> userDevices = userDeviceRepository.findByUserId(userId);
        return ResponseEntity.ok().body(userDevices);
    }

    @GetMapping("api/vacuum_cleaners?serialNumber={serialNumber}")
    public ResponseEntity<Vacuum> getVacuumBySerial(@PathVariable(value = "serialNumber") String serial)
        throws ResourceNotFoundException {
        Vacuum vacuum = vacuumRepository.findBySerialNumber(serial);
        return ResponseEntity.ok().body(vacuum);
    }

    @GetMapping("api/vacuum_cleaners/{id}/{isOn}")
    public ResponseEntity<Vacuum> getVacuumBySerial(@PathVariable(value = "id") String _id, @PathVariable(value = "isOn") String _isOn)
        throws ResourceNotFoundException {
	Long id = Long.parseLong(_id);
	Boolean isOn = Boolean.parseBoolean(_isOn);
        Vacuum vacuum = vacuumRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Vacuum not found for this id :: " + id));
	if (vacuum != null) {
	    vacuum.setIsOn(isOn);
	    vacuumRepository.save(vacuum);
	}
        return ResponseEntity.ok().body(vacuum);
    }
    
    @GetMapping("api/vacuum_cleaners/{id}")
    public ResponseEntity<Vacuum> getVacuumById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        Vacuum vacuum = vacuumRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Vacuum not found for this id :: " + id));
        return ResponseEntity.ok().body(vacuum);
    }

    @GetMapping("api/users?username={username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String userUsername)
        throws ResourceNotFoundException {
            User user = userRepository.findByUsername(userUsername);
            return ResponseEntity.ok().body(user);
    }

    @GetMapping("api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
        throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
          .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("api/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("api/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
         throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
       .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("api/devices?name={name}")
    public ResponseEntity<Device> getDeviceByName(@PathVariable(value = "name") String name)
        throws ResourceNotFoundException {
            Device device = deviceRepository.findByName(name);
            return ResponseEntity.ok().body(device);
    }


    @GetMapping("api/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") Long deviceId)
        throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
          .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));
        return ResponseEntity.ok().body(device);
    }

    @PostMapping("api/devices")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        deviceRepository.save(device);
        return ResponseEntity.ok().body(device);
    }

    @DeleteMapping("api/devices/{id}")
    public Map<String, Boolean> deleteDevice(@PathVariable(value = "id") Long deviceId)
         throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
       .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));

        deviceRepository.delete(device);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
