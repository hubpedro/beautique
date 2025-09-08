package br.com.beautique.api.dtos;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class FullAppointmentDTO  {

	private Long id;
	private LocalDateTime dateTime;
	private Boolean appointmentsOpen;
	private CustomerResponseDTO customer;
	private BeautyProcedureResponseDTO beautyProcedure;

	@Override
	public String toString() {
		return "MongoAppointmentDTO [id=" + id + ", dateTime=" + dateTime + ", appointmentsOpen=" + appointmentsOpen
				+ ", customer=" + customer + ", beautyProcedure=" + beautyProcedure + "]";
	}
}