package br.com.beautique.api.services.impl;

import br.com.beautique.api.dtos.BeautyProcedureRequestDTO;
import br.com.beautique.api.dtos.BeautyProcedureResponseDTO;
import br.com.beautique.api.entities.BeautyProcedureEntity;
import br.com.beautique.api.mapper.BeautyProcedureMapper;
import br.com.beautique.api.messaging.BeautyProcedureMessagePublisher;
import br.com.beautique.api.repository.BeautyProcedureRepository;
import br.com.beautique.api.services.interfaces.BeautyProcedureService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BeautyProcedureServiceImpl implements BeautyProcedureService {

	@Autowired
	private BeautyProcedureRepository beautyProcedureRepository;

	@Autowired
	private BeautyProcedureMapper beautyProcedureMapper;

	@Autowired
	private BeautyProcedureMessagePublisher beautyProcedureMessagePublisher;




	@Override
	public BeautyProcedureResponseDTO create(BeautyProcedureRequestDTO beautyProcedureRequestDTO) {
		BeautyProcedureEntity beautyProcedureEntity = beautyProcedureMapper.toEntity(beautyProcedureRequestDTO);

		BeautyProcedureEntity createdBeautyProcedureEntity = beautyProcedureRepository.save(beautyProcedureEntity);


		BeautyProcedureResponseDTO dto = beautyProcedureMapper.toDTO(createdBeautyProcedureEntity);

		beautyProcedureMessagePublisher.publishCreated(dto);

		return dto;
	}

	@Override
	public List<BeautyProcedureResponseDTO> read() {
		List<BeautyProcedureEntity> beautyProcedureEntityList= beautyProcedureRepository.findAll();
		return beautyProcedureMapper.toDTOList(beautyProcedureEntityList);
	}

	@Override
	public Page<BeautyProcedureResponseDTO> read(Pageable pageable) {
		Page<BeautyProcedureEntity> beautyProcedureEntityPage = beautyProcedureRepository.findAll(pageable);
		return beautyProcedureEntityPage.map(beautyProcedureMapper::toDTO);
	}

	@Override
	public BeautyProcedureResponseDTO update(BeautyProcedureRequestDTO beautyProcedureRequestDTO) {
		BeautyProcedureEntity beautyProcedureEntity = beautyProcedureMapper.toEntity(beautyProcedureRequestDTO);
		BeautyProcedureEntity updatedBeautyProcedureEntity = beautyProcedureRepository.save(beautyProcedureEntity);


		BeautyProcedureResponseDTO dto = beautyProcedureMapper.toDTO(updatedBeautyProcedureEntity);

		beautyProcedureMessagePublisher.publishUpdated(dto);

		return dto;
	}

	@Override
	public void delete(Long id) {
		Optional<BeautyProcedureEntity> beautyProcedureEntity = beautyProcedureRepository.findById(id);
		beautyProcedureEntity.ifPresentOrElse(
				entity -> {
					beautyProcedureRepository.deleteById(id);
					beautyProcedureMessagePublisher.publishDeleted(beautyProcedureMapper.toDTO(entity));
				},
				() -> {
					throw new RuntimeException("Beauty Procedure not found");
				}
		);
	}
}