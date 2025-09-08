package br.com.beautique.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeautyProcedureResponseDTO {
	private Long id;
	private String name;
	private String description;
	private Double price;
}