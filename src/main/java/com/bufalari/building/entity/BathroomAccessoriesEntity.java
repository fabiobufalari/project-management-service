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
@Table(name = "bathroom_accessories_sets") // Nome mais claro, plural
public class BathroomAccessoriesEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    // Relações com as entidades específicas de acessórios
    // Cascade ALL: se este conjunto for deletado, os acessórios também são.
    // orphanRemoval = true: se um acessório for removido da referência aqui, ele é deletado.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "shower_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bathacc_shower"))
    private ShowerEntity shower;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "sink_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bathacc_sink"))
    private SinkEntity sink;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "toilet_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_bathacc_toilet"))
    private ToiletEntity toilet;

    // Opcional: Uma referência ao cômodo (RoomEntity) ao qual este conjunto de acessórios pertence,
    // se a relação for de Room para BathroomAccessoriesSet (1-para-1 ou 1-para-muitos se um cômodo puder ter vários conjuntos).
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "room_id")
    // private RoomEntity room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BathroomAccessoriesEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}