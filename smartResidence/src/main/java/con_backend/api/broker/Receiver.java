package con_backend.api.broker;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

import con_backend.api.exception.ResourceNotFoundException;

import con_backend.api.model.Vacuum;
import con_backend.api.model.LightBulb;
import con_backend.api.model.CoffeeMachine;
import con_backend.api.model.Refrigerator;

import con_backend.api.repository.VacuumRepository;
import con_backend.api.repository.LightBulbRepository;
import con_backend.api.repository.CoffeeMachineRepository;
import con_backend.api.repository.RefrigeratorRepository;

@Component
public class Receiver {
	private CountDownLatch latch = new CountDownLatch(1);

  @Autowired
  private VacuumRepository vacuumRepository;

  @Autowired
  private LightBulbRepository lightBulbRepository;
  
  @Autowired
  private CoffeeMachineRepository coffeeMachineRepository;

  @Autowired
  private RefrigeratorRepository refrigeratorRepository;

	public void receiveMessage(String message) 
		throws ResourceNotFoundException {
		System.out.println("Received <" + message + ">");
		JSONObject objectJson = new JSONObject(message);

    switch(objectJson.get("name").toString()) {
        case "Vacuum":
            Vacuum vacuum = vacuumRepository.findById(Long.parseLong(objectJson.get("id").toString()))
              .orElseThrow(() -> new ResourceNotFoundException("vacuum not found for this id"));
            if (vacuum != null) {
              vacuum.setIsOn(Boolean.parseBoolean(objectJson.get("isOn").toString()));
              vacuum.setRemainingBattery(Integer.parseInt(objectJson.get("remainingBattery").toString()));
              vacuum.setCurrentLocation(objectJson.get("currentLocation").toString());
              vacuum.setCleaningMode(objectJson.get("cleaningMode").toString());

              vacuumRepository.save(vacuum);
            }
            break;
       case "LightBulb":
            LightBulb lightBulb = lightBulbRepository.findById(Long.parseLong(objectJson.get("id").toString()))
              .orElseThrow(() -> new ResourceNotFoundException("lightBulb not found for this id"));
            if (lightBulb!= null) {
              lightBulb.setIsOn(Boolean.parseBoolean(objectJson.get("isOn").toString()));
              lightBulb.setBrightnessLvl(Integer.parseInt(objectJson.get("brightnessLvl").toString()));
              lightBulb.setCurrent_power_usage(Integer.parseInt(objectJson.get("current_power_usage").toString()));
              lightBulb.setLocation(objectJson.get("location").toString());
              lightBulb.setColor(objectJson.get("color").toString());

              lightBulbRepository.save(lightBulb);
            }
            break;
       case "CoffeeMachine":
            CoffeeMachine coffeeMachine = coffeeMachineRepository.findById(Long.parseLong(objectJson.get("id").toString()))
              .orElseThrow(() -> new ResourceNotFoundException("coffeeMachine not found for this id"));
            if (coffeeMachine != null) {
              coffeeMachine.setIsOn(Boolean.parseBoolean(objectJson.get("isOn").toString()));
              coffeeMachine.setCoffee_time(objectJson.get("coffee_time").toString());
              coffeeMachine.setStrength(objectJson.get("strength").toString());
              coffeeMachine.setWater_lvl(Integer.parseInt(objectJson.get("water_lvl").toString()));
              coffeeMachine.setAdd_water(Boolean.parseBoolean(objectJson.get("add_water").toString()));
              coffeeMachine.setCurrent_power_usage(Integer.parseInt(objectJson.get("current_power_usage").toString()));

              coffeeMachineRepository.save(coffeeMachine);
            }
            break;
       case "Refrigerator":
            Refrigerator refrigerator = refrigeratorRepository.findById(Long.parseLong(objectJson.get("id").toString()))
              .orElseThrow(() -> new ResourceNotFoundException("coffeeMachine not found for this id"));
            if (refrigerator != null) {
              refrigerator.setAirFilter_change_date(objectJson.get("airFilter_change_date").toString());
              refrigerator.setContent_list(objectJson.get("content_list").toString());
              refrigerator.setCurrent_power_usage(Integer.parseInt(objectJson.get("current_power_usage").toString()));

              refrigeratorRepository.save(refrigerator);
            }
            break;
    }
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
