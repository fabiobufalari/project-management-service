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
@Table(name = "kitchen_accessories_sets")
public class KitchenAccessoriesEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "sink_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_kitchenacc_sink"))
    private SinkEntity sink;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "countertop_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_kitchenacc_countertop"))
    private CountertopEntity countertop;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cabinets_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_kitchenacc_cabinets"))
    private CabinetsEntity cabinets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitchenAccessoriesEntity that = (KitchenAccessoriesEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}