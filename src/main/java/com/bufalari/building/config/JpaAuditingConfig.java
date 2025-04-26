// Path: project-management-service/src/main/java/com/bufalari/building/config/JpaAuditingConfig.java
package com.bufalari.building.config;

// Importa a classe AuditorAwareImpl do pacote correto DENTRO deste microserviço
// Imports the AuditorAwareImpl class from the correct package WITHIN this microservice
import com.bufalari.building.auditing.AuditorAwareImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class to enable JPA Auditing for the Building service.
 * Classe de configuração para habilitar a Auditoria JPA para o serviço Building.
 */
@Configuration
// Garante que o 'auditorAwareRef' corresponda ao nome do bean (@Component) se você deu um nome específico
// Ensures the 'auditorAwareRef' matches the bean name (@Component) if you gave it a specific name
@EnableJpaAuditing(auditorAwareRef = "auditorProviderBuilding")
public class JpaAuditingConfig {

    /**
     * Provides the AuditorAware bean implementation.
     * Fornece a implementação do bean AuditorAware.
     *
     * @return An instance of AuditorAware<String>.
     *         Uma instância de AuditorAware<String>.
     */
    @Bean // O nome do método define o nome do bean padrão, mas @Component("...") tem precedência se usado
    public AuditorAware<String> auditorProviderBuilding() { // Nome do bean/método correspondente
        return new AuditorAwareImpl(); // Agora ele encontra a classe no pacote 'auditing'
    }
}