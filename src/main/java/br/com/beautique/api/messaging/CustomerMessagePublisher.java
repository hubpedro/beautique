package br.com.beautique.api.messaging;

import br.com.beautique.api.dtos.CustomerResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerMessagePublisher {
	private final RabbitTemplate rabbitTemplate;

	public CustomerMessagePublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void publishCreated(CustomerResponseDTO customerResponseDTO) {
		rabbitTemplate.convertAndSend("beautique-exchange", "customer.created", customerResponseDTO);
	}

	public void publishUpdated(CustomerResponseDTO customerResponseDTO) {
		rabbitTemplate.convertAndSend("beautique-exchange", "customer.updated", customerResponseDTO);

	}

	public void publishDeleted(Long id) {
		rabbitTemplate.convertAndSend("beautique-exchange", "customer.deleted", CustomerResponseDTO.builder().id(id).build());
	}

}