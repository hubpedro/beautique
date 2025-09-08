package br.com.beautique.api.services.interfaces;

import br.com.beautique.api.dtos.AppointmentRequestDTO;
import br.com.beautique.api.dtos.AppointmentResponseDTO;

public interface AppointmentsService  {
	//create, read, update, delete
	AppointmentResponseDTO create(AppointmentRequestDTO appointmentRequestDTO) throws Exception;
	AppointmentResponseDTO update(AppointmentRequestDTO appointmentRequestDTO) throws Exception;
	void delete(Long id) throws Exception;
	AppointmentResponseDTO setCustomerToAppointment(AppointmentRequestDTO appointmentRequestDTO) throws Exception;
}