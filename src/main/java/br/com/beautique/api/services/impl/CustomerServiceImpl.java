package br.com.beautique.api.services.impl;

import br.com.beautique.api.dtos.CustomerRequestDTO;
import br.com.beautique.api.dtos.CustomerResponseDTO;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.mapper.CustomerMapper;
import br.com.beautique.api.messaging.CustomerMessagePublisher;
import br.com.beautique.api.repository.CustomerRepository;
import br.com.beautique.api.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for customer operations in the Beautique beauty salon API.
 * Provides business logic for creating, updating, and managing customer entities.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
    public CustomerRepository customerRepository;

	@Autowired
	public CustomerMapper customerMapper;

	@Autowired
	public CustomerMessagePublisher customerMessagePublisher;

    @Override
    public CustomerResponseDTO createCostumerEntity(CustomerRequestDTO customerRequestDTO) {
        // Convert DTO to entity
        CustomerEntity customerEntity = customerMapper.toEntity(customerRequestDTO);
        
        // Save entity to databaese
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        // Convert saved entity back to response DTO
	    CustomerResponseDTO dto = customerMapper.toDTO(savedCustomer);

		dto.setOperationType("created");

	    customerMessagePublisher.publishCreated(dto);

	    return dto;
    }

	    @Override
	    public CustomerResponseDTO updateCustomerEntity(Long id, CustomerRequestDTO customerRequestDTO) {
	        CustomerEntity existingCustomer = customerRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
			CustomerEntity updatedCostumerEntity = customerMapper.updateEntityFromDTO(existingCustomer, customerRequestDTO);
			CustomerEntity updatedCustomer = customerRepository.save(updatedCostumerEntity);
		    CustomerResponseDTO customerResponseDTO = customerMapper.toDTO(updatedCustomer);
		    customerMessagePublisher.publishUpdated(customerResponseDTO);
		    return customerResponseDTO;
	    }

	    @Override
	    public CustomerResponseDTO getCustomerById(Long id) {
	        CustomerEntity customer = customerRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
	        return customerMapper.toDTO(customer);
	    }

	    @Override
	    public void deleteCustomerById(Long id) {
	        if (!customerRepository.existsById(id)) {
	            throw new RuntimeException("Cliente não encontrado");
	        }
	        customerRepository.deleteById(id);
		    customerMessagePublisher.publishDeleted(id);
	    }

		public List<CustomerResponseDTO> getAllCustomers(
		) {
			List<CustomerEntity> customers = customerRepository.findAll();
			return customerMapper.toDTOList(customers);
		}

		public CustomerResponseDTO patchCustomer(CustomerRequestDTO customerRequestDTO, Long id) {
		  CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(
				  () -> new RuntimeException("Customer not found")
		  );
			CustomerEntity costumerEntity = customerMapper.updateEntity(customerEntity, customerRequestDTO);
			CustomerEntity updatedCustomer = customerRepository.save(costumerEntity);
			return customerMapper.toDTO(updatedCustomer);
		}
}