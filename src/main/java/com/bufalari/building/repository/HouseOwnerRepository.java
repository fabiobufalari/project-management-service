package com.bufalari.building.repository;

import com.bufalari.building.entity.HouseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseOwnerRepository extends JpaRepository<HouseOwner, Long> {
    // Aqui podemos adicionar métodos personalizados, caso seja necessário.
}
