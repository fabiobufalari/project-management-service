package com.bufalari.building.entity;

// import com.bufalari.building.DTO.ClientContactInfoDTO; // Removido, DTO não deve ser embutido
import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
// import org.hibernate.annotations.GenericGenerator; // Não mais necessário com GenerationType.UUID

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients", indexes = {
    @Index(name = "idx_client_name", columnList = "clientName")
})
public class ClientEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 200)
    private String clientName;

    @Embedded // Informações de contato embutidas
    private ClientContactInfoEmbeddable contactInfo; // <<< CORRIGIDO para Embeddable

    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Builder.Default
    private List<HouseOwner> projectOwnerships = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // Verificação de tipo mais precisa
        ClientEntity that = (ClientEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Apenas ID se for único após persistência
    }
}