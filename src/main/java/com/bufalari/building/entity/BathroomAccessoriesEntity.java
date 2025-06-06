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
@Table(name = "bathroom_accessories_sets")
public class BathroomAccessoriesEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "shower_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bathacc_shower"))
    private ShowerEntity shower;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "sink_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bathacc_sink"))
    private SinkEntity sink;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "toilet_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bathacc_toilet"))
    private ToiletEntity toilet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BathroomAccessoriesEntity that = (BathroomAccessoriesEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}