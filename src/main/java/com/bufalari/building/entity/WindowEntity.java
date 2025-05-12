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
@Table(name = "windows")
public class WindowEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(length = 50)
    private String windowId; // ID textual/descritivo

    // Campos para dimensões originais em pés e polegadas separadas
    private double widthFoot;
    private int widthInches;
    private double heightFoot;
    private int heightInches;
    private double thicknessInch;

    // Campos para dimensões totais calculadas em pés decimais
    @Column(name = "total_width_feet")
    private Double width;
    @Column(name = "total_height_feet")
    private Double height;

    // Relação com WallEntity (se uma janela pertence a uma parede)
    // Esta FK já está definida em WallEntity com @JoinColumn(name = "wall_id") na lista @OneToMany
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "wall_id")
    // private WallEntity wall;

    @PrePersist
    @PreUpdate
    private void calculateTotalDimensions() {
        this.width = this.widthFoot + (this.widthInches / 12.0);
        this.height = this.heightFoot + (this.heightInches / 12.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof WindowEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}