package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal; // Importar
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
@Table(name = "floors", indexes = {
    @Index(name = "idx_floor_project_id", columnList = "project_id"),
    @Index(name = "idx_floor_number_project", columnList = "floorNumber, project_id", unique = true)
})
public class FloorEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "floor_id_legacy")
    private Long floorIdLegacy;

    @Column(nullable = false)
    private int floorNumber;

    @Column(name = "area_square_feet") // double sem precision/scale
    private double areaSquareFeet;

    private boolean heated;

    @Column(length = 100)
    private String material;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_floor_project"))
    private ProjectEntity project;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<RoomEntity> rooms = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloorEntity that = (FloorEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}