package br.com.beautique.api.mapper;

import br.com.beautique.api.dtos.AppointmentRequestDTO;
import br.com.beautique.api.dtos.AppointmentResponseDTO;
import br.com.beautique.api.entities.AppointmentsEntity;
import br.com.beautique.api.entities.BeautyProcedureEntity;
import br.com.beautique.api.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppointmentsMapper {

	@Mapping(source = "customer", target = "customer")
	@Mapping(source = "beautyProcedure", target = "beautyProcedure")
	AppointmentResponseDTO toDTO(AppointmentsEntity appointmentsEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(source = "customer", target = "customer") // ← CORRIGIDO: added source
	@Mapping(source = "beautyProcedure", target = "beautyProcedure") // ← CORRIGIDO: added source
	AppointmentsEntity toEntity(AppointmentRequestDTO appointmentsRequestDTO);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true) // ← CORRIGIDO: ignore=true (o JPA atualiza automaticamente)
	@Mapping(source = "customer", target = "customer") // ← CORRIGIDO: added source
	@Mapping(source = "beautyProcedure", target = "beautyProcedure") // ← CORRIGIDO: added source
	void updateEntityFromDTO(AppointmentRequestDTO dto, @MappingTarget AppointmentsEntity entity);

	// Auxiliares — CORRIGIDOS para evitar conflito de nomes
	default CustomerEntity mapToCustomer(Long id) { // ← NOME ALTERADO
		if (id == null) return null;
		CustomerEntity customer = new CustomerEntity();
		customer.setId(id);
		return customer;
	}

	default BeautyProcedureEntity mapToProcedure(Long id) { // ← NOME ALTERADO
		if (id == null) return null;
		BeautyProcedureEntity bp = new BeautyProcedureEntity();
		bp.setId(id);
		return bp;
	}

	default Long map(CustomerEntity customer) {
		return customer != null ? customer.getId() : null;
	}

	default Long map(BeautyProcedureEntity beautyProcedure) {
		return beautyProcedure != null ? beautyProcedure.getId() : null;
	}
}