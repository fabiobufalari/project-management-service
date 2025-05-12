package com.bufalari.building.repository;


import com.bufalari.building.entity.WallMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional; // Importar se adicionar métodos com Optional
// import java.util.UUID; // Não usado aqui diretamente

@Repository
public interface WallMeasurementRepository extends JpaRepository<WallMeasurementEntity, Long> { // Mantém Long ID
    // Métodos de consulta personalizados podem ser adicionados aqui, se necessário.
    // Exemplo:
    // Optional<WallMeasurementEntity> findByIdentifyWall(String identifyWall);
}