package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
// import org.hibernate.annotations.GenericGenerator; // Não é mais necessário com GenerationType.UUID

import java.math.BigDecimal; // <<<--- IMPORTAR BigDecimal
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "house_owners", indexes = {
        @Index(name = "idx_houseowner_project_id", columnList = "project_id"),
        @Index(name = "idx_houseowner_client_id", columnList = "client_id")
})
public class HouseOwner extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // <<<--- ESTRATÉGIA UUID PADRÃO
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_houseowner_project"))
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_houseowner_client"))
    private ClientEntity client;

    @Column(name = "ownership_percentage", precision = 5, scale = 2) // precision e scale são válidos para BigDecimal
    private BigDecimal ownershipPercentage; // <<<--- ALTERADO PARA BigDecimal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Usar getClass() para garantir que o tipo exato seja comparado, útil se houver herança
        if (o == null || getClass() != o.getClass()) return false;
        HouseOwner that = (HouseOwner) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        // Usar apenas o ID para hashCode se for único após a persistência
        // e a entidade for gerenciada pelo JPA.
        // Se a entidade puder estar em um Set antes de ser persistida,
        // e o ID for nulo, isso pode causar problemas.
        // Uma alternativa comum é usar Objects.hash(getClass()) para entidades não persistidas.
        // Mas para consistência com equals, se id é o diferenciador principal, usá-lo é comum.
        return Objects.hash(id);
    }
}