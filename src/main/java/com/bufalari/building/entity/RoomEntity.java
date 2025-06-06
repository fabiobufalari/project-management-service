package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "rooms", indexes = {
    @Index(name = "idx_room_floor_id", columnList = "floor_id"),
    @Index(name = "idx_room_project_id", columnList = "project_id"), // Adicionado
    // Unique constraint para roomType dentro de um andar e projeto
    @Index(name = "idx_room_type_floor_project", columnList = "roomType, floor_id, project_id", unique = true)
})
public class RoomEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String roomType;

    private boolean isWetArea;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "floor_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_floor"))
    private FloorEntity floor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Ligação direta ao projeto
    @JoinColumn(name = "project_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_room_project"))
    private ProjectEntity project;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<WallRoomMapping> wallMappings = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void ensureProjectConsistency() {
        if (this.floor != null && this.floor.getProject() != null) {
            this.project = this.floor.getProject();
        }
        if (this.project == null) {
            // Isso pode acontecer se o andar não estiver definido ou o projeto do andar não estiver definido.
            // O ideal é que floor e seu projeto sejam sempre definidos antes de salvar Room.
            throw new IllegalStateException("Room must be associated with a Project, typically via its Floor.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}