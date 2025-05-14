package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal; // Considerar BigDecimal para medidas se precisão for crítica
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String wallId;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = "length_foot_original")
    private double lengthFoot;
    @Column(name = "length_inches_original")
    private double lengthInches;
    @Column(name = "height_foot_original")
    private double heightFoot;
    @Column(name = "height_inches_original")
    private double heightInches;
    @Column(name = "wall_thickness_inch")
    private double wallThicknessInch;

    @Column(name = "stud_spacing_inch")
    private Double studSpacingInch;


    @Column(length = 100)
    private String materialType;

    @Column(name = "linear_footage")
    private double linearFootage;
    @Column(name = "square_footage")
    private double squareFootage;
    @Column(name = "stud_linear_footage")
    private double studLinearFootage;


    @Column(nullable = false)
    private boolean isExternal;

    @Column(nullable = false)
    private int floorNumber;

    @Column(name = "stud_count", nullable = false)
    @Builder.Default
    private int studCount = 0;

    private Integer numberOfPlates;


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
        if (this.project == null && this.roomMappings != null && !this.roomMappings.isEmpty() &&
            this.roomMappings.get(0).getRoom() != null && this.roomMappings.get(0).getRoom().getProject() != null) {
            this.project = this.roomMappings.get(0).getRoom().getProject();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WallEntity that = (WallEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}