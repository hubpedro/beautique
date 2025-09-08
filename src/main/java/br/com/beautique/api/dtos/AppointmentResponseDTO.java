package br.com.beautique.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
	private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	private Boolean appointmentsOpen;
	private Long customer;
	private Long beautyProcedure;
}