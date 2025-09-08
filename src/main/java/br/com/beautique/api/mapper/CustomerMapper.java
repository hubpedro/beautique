package br.com.beautique.api.mapper;

import br.com.beautique.api.dtos.CustomerRequestDTO;
import br.com.beautique.api.dtos.CustomerResponseDTO;
import br.com.beautique.api.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	CustomerResponseDTO toDTO(CustomerEntity customerEntity);

	@Mapping(target = "appointments", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	CustomerEntity toEntity(CustomerRequestDTO dto);

	List<CustomerResponseDTO> toDTOList(List<CustomerEntity> customers);

	@Mapping(target = "appointments", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	CustomerEntity updateEntityFromDTO(@MappingTarget CustomerEntity customerEntity, CustomerRequestDTO customerRequestDTO);

	@Mapping(target = "appointments", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	CustomerEntity updateEntity(@MappingTarget CustomerEntity customerEntity, CustomerRequestDTO customerRequestDTO);
}