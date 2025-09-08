package br.com.beautique.api.services.interfaces;

import br.com.beautique.api.dtos.BeautyProcedureRequestDTO;
import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BeautyProcedureService {
	BeautyProcedureResponseDTO create(BeautyProcedureRequestDTO beautyProcedureRequestDTO);
	List<BeautyProcedureResponseDTO> read();
	Page<BeautyProcedureResponseDTO> read(Pageable pageable);
	BeautyProcedureResponseDTO update(BeautyProcedureRequestDTO beautyProcedureRequestDTO);
	void delete(Long id);
}