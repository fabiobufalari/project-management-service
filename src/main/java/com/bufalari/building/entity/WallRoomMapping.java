package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wall_room_mappings",
       indexes = {
           @Index(name = "idx_wallroommap_wall_id", columnList = "wall_id"),
           @Index(name = "idx_wallroommap_room_id", columnList = "room_id")
       },
       uniqueConstraints = { // <<<--- Local CORRETO para @UniqueConstraint
           @UniqueConstraint(name = "uk_wall_room_side", columnNames = {"wall_id", "room_id", "side"})
       }
)
public class WallRoomMapping extends AuditableBaseEntity {

    @Id
	@GeneratedValue(generator = "UUID_Generator_WRM")
	@GenericGenerator(name = "UUID_Generator_WRM", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wall_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_wallroommap_wall"))
    private WallEntity wall;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_wallroommap_room"))
    private RoomEntity room;

    @Column(length = 50, nullable = false)
    private String side;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof WallRoomMapping that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}