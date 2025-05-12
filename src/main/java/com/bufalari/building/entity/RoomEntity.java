package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Importar Base
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "rooms", indexes = { // Adicionar índices
    @Index(name = "idx_room_floor_id", columnList = "floor_id"),
    @Index(name = "idx_room_type_floor", columnList = "roomType, floor_id, project_id") // Único por tipo, andar e projeto
})
public class RoomEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID é o ID principal

    // O campo 'uuid' (BYTEA) anterior foi removido pois 'id' agora é UUID.

    @Column(nullable = false, length = 100)
    private String roomType; // Tipo de cômodo (ex: "Sala de Estar", "Quarto Principal")

    // FloorNumber é importante, mas a relação com FloorEntity é mais robusta.
    // O número do andar pode ser derivado da FloorEntity associada.
    // @Column(nullable = false)
    // private int floorNumber; // Mantido para referência direta, mas FloorEntity é a fonte da verdade

    private boolean isWetArea; // Se é área molhada (banheiro, cozinha, lavanderia)

    // Relação com o Andar ao qual este cômodo pertence
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "floor_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_floor"))
    private FloorEntity floor;

    // Relação ManyToMany com Paredes, gerenciada pela tabela de junção "wall_room_mapping"
    // O 'mappedBy' deve corresponder ao nome do campo em WallRoomMapping que referencia RoomEntity.
    // Se WallRoomMapping tem 'private RoomEntity room;', então mappedBy = "room".
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<WallRoomMapping> wallMappings = new ArrayList<>();


    // Para acesso direto às paredes sem passar pelo mapeamento (opcional, pode ser @Transient)
    // @Transient
    // public List<WallEntity> getWalls() {
    //     if (wallMappings == null) return new ArrayList<>();
    //     return wallMappings.stream().map(WallRoomMapping::getWall).collect(Collectors.toList());
    // }

    // Adicionar referência ao Projeto para consistência de busca (via FloorEntity)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_project"))
    private ProjectEntity project;

    @PrePersist
    @PreUpdate
    private void ensureProjectConsistency() {
        if (this.floor != null) {
            this.project = this.floor.getProject();
        }
        if (this.project == null) {
            throw new IllegalStateException("Room must be associated with a Project via its Floor.");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof RoomEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}