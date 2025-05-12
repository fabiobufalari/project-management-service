package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Assumindo auditável
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList; // Importar
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "floor_calculation_structures") // Nome de tabela
public class CalculationStructureEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    // Este campo 'floorId' String parece redundante se esta entidade tem seu próprio ID UUID
    // e se refere a uma FloorEntity que também terá um ID UUID.
    // Se for para ligar a uma FloorEntity, um @OneToOne ou @ManyToOne para FloorEntity seria mais apropriado.
    // @Column(length = 100)
    // private String floorId; // ID textual legado do andar?

    private int floorNumber; // Número do andar
    private double areaSquareFeet;
    private boolean heated;
    @Column(length = 100)
    private String material; // Material do piso?

    // Relação com o Andar ao qual esta estrutura de cálculo pertence
    @OneToOne(fetch = FetchType.LAZY) // Ou @ManyToOne se uma FloorEntity puder ter várias CalculationStructures
    @JoinColumn(name = "floor_entity_id", foreignKey = @ForeignKey(name = "fk_calcstruct_floor")) // FK para FloorEntity.id
    private FloorEntity floor; // Referência à entidade Andar

    // Componentes do andar (relações OneToOne com cascade)
    // As entidades filhas (WallEntity, CeilingEntity, etc.) devem ter seus IDs UUID gerados.

    // Mapear WallEntity para FloorEntity diretamente é mais comum (Floor tem muitas Walls)
    // Se CalculationStructureEntity é uma tabela separada, a relação com Wall pode ser diferente.
    // Assumindo aqui que CalculationStructure "tem" essas paredes (relação de composição).
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_structure_id", foreignKey = @ForeignKey(name = "fk_wall_calcstruct")) // FK na tabela WallEntity
    @Builder.Default
    private List<WallEntity> walls = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ceiling_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_ceiling"))
    private CeilingEntity ceiling;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "baseboard_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_baseboard"))
    private BaseboardEntity baseboards;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "painting_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_painting"))
    private PaintingEntity painting;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "balcony_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_balcony"))
    private BalconyEntity balcony;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "bathroom_accessories_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_bathacc"))
    private BathroomAccessoriesEntity bathroomAccessories;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_accessories_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_kitchenacc"))
    private KitchenAccessoriesEntity kitchenAccessories;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "roof_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_roof"))
    private RoofEntity roof; // Geralmente um projeto tem um telhado, não cada andar

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof CalculationStructureEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}