package br.com.beautique.api.services.impl;

import br.com.beautique.api.dtos.AppointmentRequestDTO;
import br.com.beautique.api.dtos.AppointmentResponseDTO;
import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import br.com.beautique.api.dtos.CustomerResponseDTO;
import br.com.beautique.api.entities.AppointmentsEntity;
import br.com.beautique.api.entities.BeautyProcedureEntity;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.mapper.AppointmentsMapper;
import br.com.beautique.api.mapper.BeautyProcedureMapper;
import br.com.beautique.api.mapper.CustomerMapper;
import br.com.beautique.api.messaging.AppointmentsMessagePublisher;
import br.com.beautique.api.repository.AppointmentRepository;
import br.com.beautique.api.repository.BeautyProcedureRepository;
import br.com.beautique.api.repository.CustomerRepository;
import br.com.beautique.api.services.interfaces.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AppointmentsServiceImpl implements AppointmentsService {
	@Autowired
	private AppointmentsMapper appointmentsMapper;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private BeautyProcedureRepository beautyProcedureRepository;

	@Autowired
	private CustomerRepository customerRepository;


	@Autowired
	private BeautyProcedureMapper beautyProcedureMapper;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private AppointmentsMessagePublisher appointmentsMessagePublisher;

	@Override
	@Transactional
	public AppointmentResponseDTO create(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
		AppointmentsEntity appointmentsEntity = appointmentsMapper.toEntity(appointmentRequestDTO);
		AppointmentsEntity savedAppointmentsEntity = appointmentRepository.save(appointmentsEntity);

		appointmentsMessagePublisher.publishCreated(savedAppointmentsEntity);

		return appointmentsMapper.toDTO(savedAppointmentsEntity);
	}

	@Override
	@Transactional
	public AppointmentResponseDTO update(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
		Optional<AppointmentsEntity> appointmentsEntity = appointmentRepository.findById(appointmentRequestDTO.getId());

		if (appointmentsEntity.isEmpty()) {
			throw new RuntimeException("Appointment not found");
		}

		AppointmentsEntity existingEntity = appointmentsEntity.get();

		appointmentsMapper.updateEntityFromDTO(appointmentRequestDTO, existingEntity);

		AppointmentsEntity updatedAppointmentsEntity = appointmentRepository.save(existingEntity);

		appointmentsMessagePublisher.publishUpdated(updatedAppointmentsEntity);


		return appointmentsMapper.toDTO(updatedAppointmentsEntity);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Optional<AppointmentsEntity> appointmentsEntity = appointmentRepository.findById(id);
		if (appointmentsEntity.isEmpty()) {
			throw new RuntimeException("Appointment not found");
		}
		appointmentsEntity.ifPresent(appointmentRepository::delete);
		appointmentsMessagePublisher.publishDeleted(id);
	}

	@Override
	@Transactional
	public AppointmentResponseDTO setCustomerToAppointment(AppointmentRequestDTO appointmentRequestDTO) throws Exception {
		CustomerEntity customerEntity = customerRepository.findById(appointmentRequestDTO.getCustomer()).orElseThrow();
		BeautyProcedureEntity beautyProcedureEntity = beautyProcedureRepository.findById(appointmentRequestDTO.getBeautyProcedure()).orElseThrow();
		AppointmentsEntity appointmentsEntity = appointmentRepository.findById(appointmentRequestDTO.getId()).orElseThrow();

		appointmentsEntity.setCustomer(customerEntity);
		appointmentsEntity.setBeautyProcedure(beautyProcedureEntity);
		appointmentsEntity.setAppointmentsOpen(false);


		AppointmentsEntity updatedAppointmentsEntity = appointmentRepository.save(appointmentsEntity);

		BeautyProcedureResponseDTO beautyProcedureResponseDTO = beautyProcedureMapper.toDTO(beautyProcedureEntity);
		CustomerResponseDTO customerResponseDTO = customerMapper.toDTO(customerEntity);
		AppointmentResponseDTO appointmentResponseDTO = appointmentsMapper.toDTO(updatedAppointmentsEntity);

		appointmentsMessagePublisher.sendUpdateCustomerToAppointmentToQueue(beautyProcedureResponseDTO, customerResponseDTO, appointmentResponseDTO);

		return appointmentResponseDTO;
	}
}