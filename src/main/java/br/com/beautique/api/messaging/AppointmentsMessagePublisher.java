package br.com.beautique.api.messaging;


import br.com.beautique.api.dtos.AppointmentResponseDTO;
import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import br.com.beautique.api.dtos.CustomerResponseDTO;
import br.com.beautique.api.dtos.FullAppointmentDTO;
import br.com.beautique.api.entities.AppointmentsEntity;
import br.com.beautique.api.mapper.BeautyProcedureMapper;
import br.com.beautique.api.mapper.CustomerMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentsMessagePublisher {
	private final BeautyProcedureMapper beautyProcedureMapper;
	private final CustomerMapper customerMapper;
	private final RabbitTemplate rabbitTemplate;

	public AppointmentsMessagePublisher(RabbitTemplate rabbitTemplate, BeautyProcedureMapper beautyProcedureMapper, CustomerMapper customerMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.beautyProcedureMapper = beautyProcedureMapper;
		this.customerMapper = customerMapper;
	}

	public void publishCreated(AppointmentsEntity appt) {
		rabbitTemplate.convertAndSend("appointmentQueue", FullAppointmentDTO.builder().
				id(appt.getId())
				.appointmentsOpen(appt.getAppointmentsOpen())
				.beautyProcedure(beautyProcedureMapper.toDTO(appt.getBeautyProcedure()))
				.customer(customerMapper.toDTO(appt.getCustomer()))
				.build());
	}

	public void publishUpdated(AppointmentsEntity appt) {
		CustomerResponseDTO customerResponseDTO = customerMapper.toDTO(appt.getCustomer());
		BeautyProcedureResponseDTO beautyProcedureResponseDTO = beautyProcedureMapper.toDTO(appt.getBeautyProcedure());

		rabbitTemplate.convertAndSend("appointmentQueue", FullAppointmentDTO.builder()
				.id(appt.getId())
				.beautyProcedure(beautyProcedureResponseDTO)
				.customer(customerResponseDTO)
				.build());
	}

	public void publishDeleted(Long id) {
		rabbitTemplate.convertAndSend("appointmentQueue", FullAppointmentDTO.builder().id(id).build());
	}

	public void sendUpdateCustomerToAppointmentToQueue(BeautyProcedureResponseDTO beautyProcedureResponseDTO, CustomerResponseDTO customerResponseDTO, AppointmentResponseDTO appointmentResponseDTO) throws Exception {
		FullAppointmentDTO fullAppointmentDTO = FullAppointmentDTO.builder()
				.id(appointmentResponseDTO.getId())
				.beautyProcedure(beautyProcedureResponseDTO)
				.customer(customerResponseDTO)
				.appointmentsOpen(appointmentResponseDTO.getAppointmentsOpen())
				.build();

		rabbitTemplate.convertAndSend("appointmentQueue", fullAppointmentDTO);
	}
}