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

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private VacuumRepository vacuumRepository;

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
		Vacuum vacuum = new Vacuum(1, true, "Living-room", "Power", 50, "qwerty12345");
		vacuumRepository.save(vacuum);
	}

}
