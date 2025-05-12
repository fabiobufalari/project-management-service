package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Table(name = "walls", indexes = {
    @Index(name = "idx_wall_project_floor_wallid", columnList = "project_id, floorNumber, wallId", unique = true)
})
public class WallEntity extends AuditableBaseEntity {

    private static final Logger log = LoggerFactory.getLogger(WallEntity.class);

    public static final String TYPE_EXTERNAL = "external";
    public static final String TYPE_INTERNAL = "internal";

    @Id
	@GeneratedValue(generator = "UUID_Generator_Wall") // Nome único para o gerador
	@GenericGenerator(name = "UUID_Generator_Wall", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id;

    @Column(nullable = false, length = 50)
    private String wallId; // Identificador textual da parede

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 20)
    private String type;

    private double lengthFoot;
    private double lengthInches;
    private double heightFoot;
    private double heightInches;
    private double wallThicknessInch;

    @Column(length = 100)
    private String materialType;

    private double linearFootage;
    private double squareFootage;

    @Column(nullable = false)
    private boolean isExternal;

    @Column(nullable = false)
    private int floorNumber;

    @Column(name = "stud_count", nullable = false)
    @Builder.Default
    private int studCount = 0;

    @Column(name = "stud_linear_footage", nullable = false)
    @Builder.Default
    private double studLinearFootage = 0.0;

    private Integer numberOfPlates;
    private Double studSpacingInch;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "wall_id", foreignKey = @ForeignKey(name = "fk_window_wall"))
    @Builder.Default
    private List<WindowEntity> windows = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "wall_id", foreignKey = @ForeignKey(name = "fk_door_wall"))
    @Builder.Default
    private List<DoorEntity> doors = new ArrayList<>();

    @OneToMany(mappedBy = "wall", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<WallRoomMapping> roomMappings = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_wall_project_main"))
    private ProjectEntity project;

    @Transient
    public double getTotalLengthInFeet() {
        return this.lengthFoot + (this.lengthInches / 12.0);
    }

    @Transient
    public double getTotalHeightInFeet() {
        return this.heightFoot + (this.heightInches / 12.0);
    }

    @PrePersist
    @PreUpdate
    private void updateCalculatedFieldsAndConsistency() {
        this.isExternal = TYPE_EXTERNAL.equalsIgnoreCase(this.type);

        if (this.project == null) {
             if (this.roomMappings != null && !this.roomMappings.isEmpty() &&
                 this.roomMappings.get(0).getRoom() != null &&
                 this.roomMappings.get(0).getRoom().getProject() != null) {
                this.project = this.roomMappings.get(0).getRoom().getProject();
                log.debug("Project ID {} set for Wall (Textual ID: {}) via RoomMapping.",
                          this.project != null ? this.project.getId() : "NULL_PROJECT", this.wallId);
             } else {
                // Log com ID textual da parede se o ID UUID ainda não estiver definido
                log.warn("WallEntity (Textual ID: {}) is being saved without an explicit Project reference or derivable from RoomMappings.",
                          this.getWallId()); // Use getWallId() para evitar NullPointer se this.wallId for nulo
             }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof WallEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}