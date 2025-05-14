package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "doors")
public class DoorEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(length = 50)
    private String doorId;

    @Column(name = "width_foot_original")
    private double widthFoot;
    private int widthInches;
    @Column(name = "height_foot_original")
    private double heightFoot;
    private int heightInches;
    @Column(name = "thickness_inch")
    private double thicknessInch;

    @Column(name = "total_width_feet")
    private Double width;
    @Column(name = "total_height_feet")
    private Double height;

    @PrePersist
    @PreUpdate
    private void calculateTotalDimensions() {
        if (this.widthInches < 0) this.widthInches = 0;
        if (this.heightInches < 0) this.heightInches = 0;
        this.width = this.widthFoot + (this.widthInches / 12.0);
        this.height = this.heightFoot + (this.heightInches / 12.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoorEntity that = (DoorEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}