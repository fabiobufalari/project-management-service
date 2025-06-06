package com.constructionhub.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project Service Application
 * 
 * EN: Main entry point for the Project Service microservice.
 * This service manages construction projects, their tasks, and related information.
 * 
 * PT: Ponto de entrada principal para o microsserviço de Serviço de Projetos.
 * Este serviço gerencia projetos de construção, suas tarefas e informações relacionadas.
 */
@SpringBootApplication
public class ProjectServiceApplication {

    /**
     * Main method
     * 
     * EN: Bootstraps the Spring Boot application for the Project Service.
     * 
     * PT: Inicializa a aplicação Spring Boot para o Serviço de Projetos.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class, args);
    }
}
