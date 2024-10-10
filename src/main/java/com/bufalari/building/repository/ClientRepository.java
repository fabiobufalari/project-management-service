package com.bufalari.building.repository;

import com.bufalari.building.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    // Aqui você pode adicionar métodos customizados, como buscar por nome do cliente
}
