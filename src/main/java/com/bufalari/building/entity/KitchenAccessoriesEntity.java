package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Assumindo auditável
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
@Table(name = "kitchen_accessories_sets") // Nome de tabela
public class KitchenAccessoriesEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "sink_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_kitchenacc_sink"))
    private SinkEntity sink;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "countertop_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_kitchenacc_countertop"))
    private CountertopEntity countertop;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cabinets_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_kitchenacc_cabinets"))
    private CabinetsEntity cabinets;

    // Opcional: Referência ao cômodo (RoomEntity) ou andar (FloorEntity)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "room_id") // ou floor_id
    // private RoomEntity room; // ou FloorEntity floor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof KitchenAccessoriesEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}