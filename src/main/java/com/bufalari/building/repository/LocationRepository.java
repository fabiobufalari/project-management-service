package com.bufalari.building.repository;

import com.bufalari.building.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {
    // Adicionar métodos de busca customizados se necessário
    // Ex: Optional<LocationEntity> findByAddressAndCityAndPostalCode(String address, String city, String postalCode);
}