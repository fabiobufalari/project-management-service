package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Importar Base
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @Index(name = "idx_floor_number_project", columnList = "floorNumber, project_id", unique = true) // Andar único por projeto
})
public class FloorEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(name = "floor_id_legacy") // ID Long legado, se necessário
    private Long floorIdLegacy;

    @Column(nullable = false)
    private int floorNumber; // Número do andar (ex: 0 para térreo, 1 para primeiro, -1 para subsolo)

    private double areaSquareFeet;
    private boolean heated; // Se o andar tem aquecimento

    @Column(length = 100)
    private String material; // Material predominante do piso, por exemplo

    // Não está claro se `isWetArea` pertence ao Andar ou a Cômodos específicos dentro do andar.
    // Se um andar inteiro pode ser classificado como área molhada, mantenha.
    // Senão, este campo deve estar em RoomEntity.
    // private boolean isWetArea;

    // Relação com ProjectEntity (Muitos Andares para Um Projeto)
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Andar DEVE pertencer a um projeto
    @JoinColumn(name = "project_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_floor_project"))
    private ProjectEntity project;

    // Relação com Paredes (Um Andar tem Muitas Paredes)
    // Se WallEntity tiver uma referência @ManyToOne para FloorEntity (mappedBy="floor"),
    // então esta lista é o lado inverso.
    // Se a relação for gerenciada por uma tabela de junção (como no RoomEntity com WallEntity),
    // a anotação @ManyToMany seria aqui.
    // A entidade WallRoomMapping sugere que WallEntity e RoomEntity têm uma relação ManyToMany.
    // Um Floor pode ter uma lista de Rooms, e cada Room tem suas Walls.
    // Ou um Floor pode ter uma lista direta de Walls que definem seu perímetro externo/interno principal.

    // OPÇÃO 1: Andar tem uma lista de Cômodos (RoomEntity)
    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<RoomEntity> rooms = new ArrayList<>();

    // OPÇÃO 2: Se CalculationStructureEntity for usada para detalhar componentes do andar
    // @OneToOne(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // private CalculationStructureEntity calculationStructure;


    // A lista `walls` aqui com `@ManyToMany` e `@JoinTable(name = "wall_room")` estava em `RoomEntity`.
    // Se um andar tem paredes diretamente (sem passar por cômodos), a relação seria diferente.
    // A estrutura atual com `RoomEntity` tendo `walls` parece mais correta para detalhamento.
    // private List<WallEntity> walls = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FloorEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}