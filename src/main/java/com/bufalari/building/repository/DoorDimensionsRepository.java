package com.bufalari.building.repository;


import com.bufalari.building.entity.DoorDimensionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar

@Repository
public interface DoorDimensionsRepository extends JpaRepository<DoorDimensionsEntity, Long> { // Mantém Long ID
    // Métodos de consulta personalizados podem ser adicionados aqui, se necessário.
    // Exemplo:
    // List<DoorDimensionsEntity> findByWallMeasurementId(Long wallMeasurementId);
}