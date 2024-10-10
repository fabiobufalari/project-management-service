package com.bufalari.building.repository;

import com.bufalari.building.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    // Caso precise de consultas específicas, você pode adicionar métodos customizados aqui.
}
