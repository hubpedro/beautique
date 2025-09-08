package br.com.beautique.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "beauty_procediments")
public class BeautyProcedureEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Nome do procedimento é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	@Column(nullable = false, length = 100)
	private String name;

	@NotBlank(message = "Descrição é obrigatória")
	@Size(min = 10, max = 255, message = "Descrição deve ter entre 10 e 255 caracteres")
	@Column(nullable = false, length = 255)
	private String description;

	@NotNull(message = "Preço é obrigatório")
	@DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
	@Column(nullable = false)
	private Double price;

	@JsonIgnore
	@OneToMany(mappedBy = "beautyProcedure", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<AppointmentsEntity> appointments;

	@CreationTimestamp
	@Column
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;

	public String toString() {
		return "BeautyProcedureEntity [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price;
	}
}