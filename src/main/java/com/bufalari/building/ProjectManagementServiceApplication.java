package com.bufalari.building;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Keep if calling other services / Mantenha se for chamar outros serviços

/**
 * Main application class for the Project Management Service.
 * Handles project details, structure, budget, status, and links to costs/revenues.
 * Classe principal da aplicação para o Serviço de Gerenciamento de Projetos.
 * Gerencia detalhes do projeto, estrutura, orçamento, status e links para custos/receitas.
 */
@SpringBootApplication
// Enable Feign client capability if this service needs to call others (e.g., auth, client, supplier)
// Habilita a capacidade de cliente Feign se este serviço precisar chamar outros (ex: auth, client, supplier)
@EnableFeignClients // Add basePackages = "com.bufalari.building.client" if your clients are in a specific package
// @EnableJpaAuditing // REMOVED: Auditing is now configured in JpaAuditingConfig / REMOVIDO: Auditoria agora é configurada em JpaAuditingConfig
public class ProjectManagementServiceApplication { // <<--- RENAMED CLASS / CLASSE RENOMEADA ---<<<

	public static void main(String[] args) {
		// Run the Spring Boot application, referencing the renamed class
		// Executa a aplicação Spring Boot, referenciando a classe renomeada
		SpringApplication.run(ProjectManagementServiceApplication.class, args);
	}

}