package con_backend.api.broker;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

import con_backend.api.exception.ResourceNotFoundException;
import con_backend.api.repository.VacuumRepository;
import con_backend.api.model.Vacuum;

@Component
public class Receiver {
	private CountDownLatch latch = new CountDownLatch(1);

        @Autowired
	private VacuumRepository vacuumRepository;

	public void receiveMessage(String message) 
		throws ResourceNotFoundException {
		System.out.println("Received <" + message + ">");
		JSONObject toJson = new JSONObject(message);

		Vacuum vacuum = vacuumRepository.findById(Long.parseLong(toJson.get("id").toString()))
		  .orElseThrow(() -> new ResourceNotFoundException("vacuum not found for this id"));
		if (vacuum != null) {
			vacuum.setIsOn(Boolean.parseBoolean(toJson.get("isOn").toString()));
			vacuum.setRemainingBattery(Integer.parseInt(toJson.get("remainingBattery").toString()));
			vacuum.setCurrentLocation(toJson.get("currentLocation").toString());
			vacuum.setCleaningMode(toJson.get("cleaningMode").toString());

			vacuumRepository.save(vacuum);
		}
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
