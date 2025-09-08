package br.com.beautique.api.services.interfaces;

import br.com.beautique.api.dtos.CustomerRequestDTO;
import br.com.beautique.api.dtos.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
	CustomerResponseDTO createCostumerEntity(CustomerRequestDTO customerRequestDTO);
	CustomerResponseDTO getCustomerById(Long id);
	CustomerResponseDTO updateCustomerEntity(Long id, CustomerRequestDTO customerRequestDTO);
	void deleteCustomerById(Long id);
	List<CustomerResponseDTO> getAllCustomers();
}