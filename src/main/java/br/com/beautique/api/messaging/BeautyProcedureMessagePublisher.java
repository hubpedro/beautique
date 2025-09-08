package br.com.beautique.api.messaging;

import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BeautyProcedureMessagePublisher {
	private final RabbitTemplate rabbitTemplate;


	public BeautyProcedureMessagePublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	public void publishCreated(BeautyProcedureResponseDTO beautyProcedureResponseDTO) {
		rabbitTemplate.convertAndSend("beautyProcedureQueue", beautyProcedureResponseDTO);
	}

	public void publishUpdated(BeautyProcedureResponseDTO beautyProcedureResponseDTO) {
		rabbitTemplate.convertAndSend("beautyProcedureQueue", beautyProcedureResponseDTO);
	}

	public void publishDeleted(BeautyProcedureResponseDTO beautyProcedureResponseDTO) {
		rabbitTemplate.convertAndSend("beautyProcedureQueue", beautyProcedureResponseDTO);
	}
}