package com.bufalari.building.config;

import com.bufalari.building.auditing.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProviderBuilding") // Ref é "auditorProviderBuilding"
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProviderBuilding() { // Nome do bean é "auditorProviderBuilding"
        return new AuditorAwareImpl(); // Localizado em 'auditing' - OK
    }
}