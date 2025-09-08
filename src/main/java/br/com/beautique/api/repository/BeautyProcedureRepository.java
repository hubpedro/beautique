package br.com.beautique.api.repository;

import br.com.beautique.api.entities.BeautyProcedureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeautyProcedureRepository extends JpaRepository<BeautyProcedureEntity, Long> {
}