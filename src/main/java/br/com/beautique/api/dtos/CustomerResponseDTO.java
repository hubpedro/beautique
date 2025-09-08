package br.com.beautique.api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@Builder
public class CustomerResponseDTO {

	public Long id;
	public String name;
	public String email;
	public String phone;
	private String operationType;

	@Override
	public String toString() {
		return "CustomerResponseDTO [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", operationType=" + operationType + "]";
	}
}