package con_backend.api;

import con_backend.api.broker.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import con_backend.api.model.Vacuum;
import con_backend.api.repository.VacuumRepository;
import con_backend.api.model.UserDevice;
import con_backend.api.repository.UserDeviceRepository;
import con_backend.api.model.User;
import con_backend.api.repository.UserRepository;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private VacuumRepository vacuumRepository;

	@Autowired
	private UserDeviceRepository userDeviceRepository;

	@Autowired
	private UserRepository userRepository;

	public static final String exchangeName = "";

	public static final String queueName = "test_routing_key";

	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean 
	DirectExchange exchange() {
		return new DirectExchange(exchangeName);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("test_routing_key");
	}

	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Vacuum vacuum = new Vacuum(true, "Living-room", "Power", 50);
		vacuumRepository.save(vacuum);

		User user = new User("user", "{noop}password", "user@mail.com");
		userRepository.save(user);

		UserDevice userDevice = new UserDevice(user.getId(), vacuum.getId(), "Vacuum", vacuum.getSerialNumber());
		userDeviceRepository.save(userDevice);

		Vacuum vacuum1 = new Vacuum(true, "Living-room", "Power", 50);
		vacuumRepository.save(vacuum1);
		UserDevice userDevice1 = new UserDevice(user.getId(), vacuum1.getId(), "Vacuum", vacuum1.getSerialNumber());
		userDeviceRepository.save(userDevice1);
	}

}
