package br.com.beautique.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "appointments")
public class AppointmentsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Future(message = "Data e hora devem ser no futuro")
	@Column(nullable = false, updatable = true)
	private LocalDateTime dateTime;

	@NotNull(message = "Status do agendamento é obrigatório")
	@Column(nullable = false)
	private Boolean appointmentsOpen;

	@NotNull(message = "Cliente é obrigatório")
	@ManyToOne()
	@JoinColumn(name = "customer_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private CustomerEntity customer;

	@NotNull(message = "Procedimento de beleza é obrigatório")
	@ManyToOne()
	@JoinColumn(name = "beauty_procedure_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private BeautyProcedureEntity beautyProcedure;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}