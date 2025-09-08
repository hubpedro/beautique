package br.com.beautique.api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	private final String exchangeName = "beautique-exchange";

	private final String queueName = "customerQueue";
	private final String queueBeautyProcedureQueue = "beautyProcedureQueue";
	private final String appointmentsQueue = "appointmentQueue";

	private final String customerRoutingKey = "customer.*";
	private final String appointmentsRoutingKey = "appointments.*";
	private final String beautyProcedureRoutingKey = "beautyProcedure.*";


	@Bean
	public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}

	@Bean
	public Queue customerQueue() {
		return new Queue(queueName, true);
	}

	@Bean
	Binding bindingCustomer(Queue customerQueue, TopicExchange exchange) {
		return BindingBuilder.bind(customerQueue).to(exchange).with(customerRoutingKey);
	}

	@Bean
	public Queue beautyProcedureQueue() {
		return new Queue(queueBeautyProcedureQueue, true);
	}

	@Bean
	public Binding bindingBeautyProcedure(Queue beautyProcedureQueue, TopicExchange exchange) {
		return BindingBuilder.bind(beautyProcedureQueue).to(exchange).with(beautyProcedureRoutingKey);
	}

	@Bean
	public Queue appointmentQueue() {
		return new Queue(appointmentsQueue, true);
	}

	@Bean
	public Binding bindingAppointments(Queue appointmentQueue, TopicExchange exchange) {
		return BindingBuilder.bind(appointmentQueue).to(exchange).with(appointmentsRoutingKey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(jsonMessageConverter());
		return factory;
	}
}