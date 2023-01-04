package con_backend.api.broker;

import java.util.concurrent.TimeUnit;

import con_backend.api.ApiApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner{
	
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	public void run(String... args) throws Exception {
		System.out.println("Sending Message...");
		// rabbitTemplate.convertAndSend(ApiApplication.exchangeName, "test_routing_key", "Hello from RabbitMQ!");
		//receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
									
	}
}
