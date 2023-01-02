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
import con_backend.api.model.User;
import con_backend.api.model.UserDevice;
import con_backend.api.model.Vacuum;
import con_backend.api.model.CoffeeMachine;
import con_backend.api.model.LightBulb;
import con_backend.api.model.MovementSensor;
import con_backend.api.model.Thermostat;
import con_backend.api.model.Refrigerator;

import con_backend.api.repository.UserRepository;
import con_backend.api.repository.UserDeviceRepository;
import con_backend.api.repository.DeviceRepository;
import con_backend.api.repository.VacuumRepository;
import con_backend.api.repository.CoffeeMachineRepository;
import con_backend.api.repository.LightBulbRepository;
import con_backend.api.repository.MovementSensorRepository;
import con_backend.api.repository.ThermostatRepository;
import con_backend.api.repository.RefrigeratorRepository;

import con_backend.api.exception.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import con_backend.api.MyUserPrincipal;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private VacuumRepository vacuumRepository;

    @Autowired
    private CoffeeMachineRepository coffeeMachineRepository;

    @Autowired
    private LightBulbRepository lightBulbRepository;

    @Autowired
    private MovementSensorRepository movementSensorRepository;

    @Autowired
    private ThermostatRepository thermostatRepository;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;


    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/")
    public String index(@AuthenticationPrincipal MyUserPrincipal user) {
        return "index";
    }

    @GetMapping("api/listUserDevices")
    public ResponseEntity<List<UserDevice>> getDevicesByUserId(@AuthenticationPrincipal MyUserPrincipal user)
        throws ResourceNotFoundException {
        List<UserDevice> userDevices = userDeviceRepository.findByUserId(user.getId());
        return ResponseEntity.ok().body(userDevices);
    }

    // FIXIT
    @GetMapping("api/listUserDevices/{userId}")
    public ResponseEntity<List<UserDevice>> getDevicesByUserId(@PathVariable(value = "userId") Long userId)
        throws ResourceNotFoundException {
        List<UserDevice> userDevices = userDeviceRepository.findByUserId(userId);
        return ResponseEntity.ok().body(userDevices);
    }

    @GetMapping("api/getDevicesCount")
    public ResponseEntity<Integer> getDevicesCount() {
      int count = 0;
      count += vacuumRepository.count();
      count += coffeeMachineRepository.count();
      count += lightBulbRepository.count();
      count += movementSensorRepository.count();
      count += thermostatRepository.count();
      count += refrigeratorRepository.count();
      return ResponseEntity.ok().body(count);
    }

    @GetMapping("api/getDevices")
    public ResponseEntity<List<Object>> getDevices()
        throws ResourceNotFoundException {
        List<Vacuum> vacuum_cleaners = vacuumRepository.findAll();
        List<LightBulb> light_bulbs = lightBulbRepository.findAll();
        List<CoffeeMachine> coffee_machines = coffeeMachineRepository.findAll();
        List<Object> devices = List.of(vacuum_cleaners, light_bulbs, coffee_machines);
        return ResponseEntity.ok().body(devices);
    }

    // TODO: Should be create device
    @GetMapping("api/createVacuumCleaner")
    public ResponseEntity<List<UserDevice>> createVacuumCleaner(@AuthenticationPrincipal MyUserPrincipal user) {
        Vacuum newVacuum = new Vacuum();
        vacuumRepository.save(newVacuum);
        UserDevice userDevice = new UserDevice(user.getId(), newVacuum.getId(), "Vacuum", newVacuum.getSerialNumber());
        userDeviceRepository.save(userDevice);
        List<UserDevice> userDevices = userDeviceRepository.findByUserId(user.getId());
        return ResponseEntity.ok().body(userDevices);
    }

    // FIXIT: does this have to exist?
    @GetMapping("api/vacuum_cleaners?serialNumber={serialNumber}")
    public ResponseEntity<Vacuum> getVacuumBySerial(@PathVariable(value = "serialNumber") String serial)
        throws ResourceNotFoundException {
        Vacuum vacuum = vacuumRepository.findBySerialNumber(serial);
        return ResponseEntity.ok().body(vacuum);
    }

    @GetMapping("api/vacuum_cleaners")
    public ResponseEntity<List<Vacuum>> getAllVacuums()
        throws ResourceNotFoundException {
        List<Vacuum> vacuum_cleaners = vacuumRepository.findAll();
        return ResponseEntity.ok().body(vacuum_cleaners);
    }


    @GetMapping("api/vacuum_cleaners/{id}/{isOn}")
    public ResponseEntity<Vacuum> setOnOffVacuumCleaner(@PathVariable(value = "id") String _id, @PathVariable(value = "isOn") String _isOn)
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

    @GetMapping("api/light_bulbs/{id}/{isOn}")
    public ResponseEntity<LightBulb> setOnOffLightBulb(@PathVariable(value = "id") String _id, @PathVariable(value = "isOn") String _isOn)
        throws ResourceNotFoundException {
        Long id = Long.parseLong(_id);
        Boolean isOn = Boolean.parseBoolean(_isOn);
        LightBulb lightBulb = lightBulbRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("LightBulb not found for this id :: " + id));
        if (lightBulb != null) {
            lightBulb.setIsOn(isOn);
            lightBulbRepository.save(lightBulb);
        }
        return ResponseEntity.ok().body(lightBulb);
    }

    @GetMapping("api/coffee_machines/{id}/{isOn}")
    public ResponseEntity<CoffeeMachine> setOnOffCoffeeMachine(@PathVariable(value = "id") String _id, @PathVariable(value = "isOn") String _isOn)
        throws ResourceNotFoundException {
        Long id = Long.parseLong(_id);
        Boolean isOn = Boolean.parseBoolean(_isOn);
        CoffeeMachine coffeeMachine = coffeeMachineRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("CoffeeMachine not found for this id :: " + id));
        if (coffeeMachine != null) {
            coffeeMachine.setIsOn(isOn);
            coffeeMachineRepository.save(coffeeMachine);
        }
        return ResponseEntity.ok().body(coffeeMachine);
    }
    
    /* API/DEVICE/ID */
    // TODO: Handle authentication
    @GetMapping("api/vacuum_cleaners/{id}")
    public ResponseEntity<Vacuum> getVacuumById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        Vacuum vacuum = vacuumRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Vacuum not found for this id :: " + id));
        return ResponseEntity.ok().body(vacuum);
    }

    @GetMapping("api/coffee_machines/{id}")
    public ResponseEntity<CoffeeMachine> getCoffeeMachineById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        CoffeeMachine coffeeMachine = coffeeMachineRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("CoffeeMachine not found for this id :: " + id));
        return ResponseEntity.ok().body(coffeeMachine);
    }

    @GetMapping("api/light_bulbs/{id}")
    public ResponseEntity<LightBulb> getLightBulbById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        LightBulb lightBulb = lightBulbRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("LightBulb not found for this id :: " + id));
        return ResponseEntity.ok().body(lightBulb);
    }

    @GetMapping("api/movement_sensors/{id}")
    public ResponseEntity<MovementSensor> getMovementSensorById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        MovementSensor movementSensor = movementSensorRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("MovementSensor not found for this id :: " + id));
        return ResponseEntity.ok().body(movementSensor);
    }

    @GetMapping("api/refrigerators/{id}")
    public ResponseEntity<Refrigerator> getRefrigeratorById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        Refrigerator refrigerator = refrigeratorRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Refrigerator not found for this id :: " + id));
        return ResponseEntity.ok().body(refrigerator);
    }

    @GetMapping("api/thermostats/{id}")
    public ResponseEntity<Thermostat> getThermostatById(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
        Thermostat thermostat = thermostatRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Thermostat not found for this id :: " + id));
        return ResponseEntity.ok().body(thermostat);
    }

    /* USER STUFF */
    @GetMapping("api/getUsername")
    public ResponseEntity<String> getUserByUsername(@AuthenticationPrincipal MyUserPrincipal user)
        throws ResourceNotFoundException {
        User finalUser = userRepository.findById(user.getId())
          .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + user.getId()));
        return ResponseEntity.ok().body("{\"username\": \"" + finalUser.getUsername() + "\"}");
    }

    @GetMapping("api/users/{username}")
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

    // FIXIT: does this have to exist?
    @GetMapping("api/devices?name={name}")
    public ResponseEntity<Device> getDeviceByName(@PathVariable(value = "name") String name)
        throws ResourceNotFoundException {
            Device device = deviceRepository.findByName(name);
            return ResponseEntity.ok().body(device);
    }

    // FIXIT: does this have to exist?
    @GetMapping("api/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") Long deviceId)
        throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
          .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + deviceId));
        return ResponseEntity.ok().body(device);
    }

    // FIXIT: does this have to exist?
    @PostMapping("api/devices")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        deviceRepository.save(device);
        return ResponseEntity.ok().body(device);
    }

    // FIXIT: does this have to exist?
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
