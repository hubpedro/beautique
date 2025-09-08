package br.com.beautique.api.controllers;

import br.com.beautique.api.dtos.BeautyProcedureRequestDTO;
import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import br.com.beautique.api.services.impl.BeautyProcedureServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/beautyProcedure")
public class BeautyProcedureController {

	@Autowired
	private BeautyProcedureServiceImpl beautyProcedureServiceImpl;


	@PostMapping("/create")
	public ResponseEntity<BeautyProcedureResponseDTO> createBeautyProcedure(@Valid @RequestBody BeautyProcedureRequestDTO beautyProcedureRequestDTO){

		BeautyProcedureResponseDTO createdBeautyProcedure = beautyProcedureServiceImpl.create(beautyProcedureRequestDTO);

		return ResponseEntity.ok(createdBeautyProcedure);
	}

	@PatchMapping("/patch/{id}")
	public ResponseEntity<BeautyProcedureResponseDTO> updateBeautyProcedure(@RequestBody BeautyProcedureRequestDTO beautyProcedureRequestDTO, @PathVariable Long id) {
		BeautyProcedureResponseDTO updatedBeautyProcedure = beautyProcedureServiceImpl.update(beautyProcedureRequestDTO);
		return ResponseEntity.ok(updatedBeautyProcedure);
	}


	@GetMapping("/read")
	public ResponseEntity<Page<BeautyProcedureResponseDTO>> read(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {
		
		Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		
		Page<BeautyProcedureResponseDTO> beautyProcedures = beautyProcedureServiceImpl.read(pageable);
		
		return ResponseEntity.ok(beautyProcedures);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		beautyProcedureServiceImpl.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/patch")
	public ResponseEntity<BeautyProcedureResponseDTO> update(@RequestBody BeautyProcedureRequestDTO beautyProcedureRequestDTO) {
		return ResponseEntity.ok(beautyProcedureServiceImpl.update(beautyProcedureRequestDTO));
	}


}