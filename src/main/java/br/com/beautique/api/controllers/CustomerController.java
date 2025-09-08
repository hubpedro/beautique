package br.com.beautique.api.controllers;

import br.com.beautique.api.dtos.CustomerRequestDTO;
import br.com.beautique.api.dtos.CustomerResponseDTO;
import br.com.beautique.api.services.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerService;


	@PostMapping("/create")
	public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){
		CustomerResponseDTO createdCustomer = customerService.createCostumerEntity(customerRequestDTO);
		return ResponseEntity.ok(createdCustomer);
	}

	@PatchMapping("/patch/{id}")
	public ResponseEntity<CustomerResponseDTO> updateCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO, @PathVariable Long id) {
		CustomerResponseDTO updatedCustomer = customerService.patchCustomer(customerRequestDTO, id);
		return ResponseEntity.ok(updatedCustomer);
	}


}