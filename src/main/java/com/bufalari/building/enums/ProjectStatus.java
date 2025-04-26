package com.bufalari.building.enums;

/**
 * Enum representing the possible statuses of a construction project.
 * Enum representando os possíveis status de um projeto de construção.
 */
public enum ProjectStatus {
    PLANNING("Planning", "Planejamento"),
    IN_PROGRESS("In Progress", "Em Andamento"),
    ON_HOLD("On Hold", "Em Espera"),
    COMPLETED("Completed", "Concluído"),
    CANCELED("Canceled", "Cancelado");

    private final String descriptionEn;
    private final String descriptionPt;

    ProjectStatus(String descriptionEn, String descriptionPt) {
        this.descriptionEn = descriptionEn;
        this.descriptionPt = descriptionPt;
    }

    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionPt() { return descriptionPt; }
}