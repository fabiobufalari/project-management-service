package com.bufalari.building.enums;

/**
 * Enum representing the possible statuses of a construction project.
 * Enum representando os possíveis status de um projeto de construção.
 */
public enum ProjectStatus {
    PLANNING("Planning", "Planejamento"),                      // Projeto em fase de planejamento
    IN_PROGRESS("In Progress", "Em Andamento"),                // Projeto em execução
    ON_HOLD("On Hold", "Em Espera / Pausado"),                 // Projeto pausado temporariamente
    COMPLETED("Completed", "Concluído"),                      // Projeto finalizado com sucesso
    CANCELED("Canceled", "Cancelado"),                        // Projeto cancelado
    ARCHIVED("Archived", "Arquivado");                        // Projeto concluído e arquivado (histórico)

    private final String descriptionEn;
    private final String descriptionPt;

    ProjectStatus(String descriptionEn, String descriptionPt) {
        this.descriptionEn = descriptionEn;
        this.descriptionPt = descriptionPt;
    }

    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionPt() { return descriptionPt; }

    // Opcional: Método para obter descrição baseada no Locale
    public String getDescription(java.util.Locale locale) {
        // Lógica simples, poderia ser mais robusta
        if (java.util.Locale.CANADA.equals(locale) || java.util.Locale.US.equals(locale) || java.util.Locale.UK.equals(locale)) {
            return descriptionEn;
        }
        return descriptionPt; // Default para pt-BR ou outros
    }
}