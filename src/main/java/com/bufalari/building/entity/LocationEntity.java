package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Importar
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter // Usar Getter/Setter individuais ou @Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Adicionar Builder
@Table(name = "locations") // Nome da tabela
public class LocationEntity extends AuditableBaseEntity { // <<< Herda Auditoria

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(length = 255)
    private String address;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String province; // Estado/Província
    @Column(length = 20)
    private String postalCode;

    // O construtor que não incluía ID foi removido, pois o ID é a chave primária
    // e será gerenciado pelo JPA/Hibernate (ou definido explicitamente se necessário).
    // Se precisar de um construtor para criar sem ID (para que seja gerado),
    // o construtor @NoArgsConstructor + @Builder (ou setters) já cobre isso.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof LocationEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}