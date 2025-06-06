package com.bufalari.building;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Ativa application-test.yml
class ProjectManagementServiceApplicationTests { // Nome da classe consistente

	@Test
	void contextLoads() {
		// Este teste verifica se o contexto da aplicação carrega sem erros.
	}

}