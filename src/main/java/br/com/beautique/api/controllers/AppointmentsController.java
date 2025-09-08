package br.com.beautique.api.controllers;


import br.com.beautique.api.dtos.AppointmentRequestDTO;
import br.com.beautique.api.dtos.AppointmentResponseDTO;
import br.com.beautique.api.services.impl.AppointmentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

	@Autowired
	private AppointmentsServiceImpl appointmentsService;


	@PostMapping
	public ResponseEntity<AppointmentResponseDTO> create(@RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
		AppointmentResponseDTO createdAppointment = appointmentsService.create(appointmentRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
	}

	@PatchMapping
	ResponseEntity<AppointmentResponseDTO> update(@RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
		return ResponseEntity.ok(appointmentsService.update(appointmentRequestDTO));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		appointmentsService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	ResponseEntity<AppointmentResponseDTO> setCustomerToAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
		return ResponseEntity.ok(appointmentsService.update(appointmentRequestDTO));
	}

}