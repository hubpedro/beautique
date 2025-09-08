package br.com.beautique.api.mapper;

import br.com.beautique.api.dtos.BeautyProcedureRequestDTO;
import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import br.com.beautique.api.entities.BeautyProcedureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeautyProcedureMapper {
	BeautyProcedureResponseDTO toDTO(BeautyProcedureEntity beautyProcedureEntity);

	@Mapping(target="appointments", ignore=true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	BeautyProcedureEntity toEntity(BeautyProcedureRequestDTO beautyProcedureRequestDTO);

	List<BeautyProcedureResponseDTO> toDTOList(List<BeautyProcedureEntity> beautyProcedureEntities);
}