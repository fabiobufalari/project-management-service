package com.bufalari.building.enums;

/**
 * Enum to represent the side of a wall, typically in relation to a room or exterior.
 * Enum para representar o lado de uma parede, tipicamente em relação a um cômodo ou exterior.
 */
public enum SideOfWall {
    INTERNAL_ROOM_A("Internal - Room A Side", "Interno - Lado Cômodo A"),  // Lado interno de uma parede divisória, voltado para um cômodo A
    INTERNAL_ROOM_B("Internal - Room B Side", "Interno - Lado Cômodo B"),  // Lado interno de uma parede divisória, voltado para um cômodo B
    INTERNAL("Internal", "Interno"),                                     // Parede interna (genérico, se não especificar os lados)
    EXTERNAL("External", "Externo"),                                     // Lado externo de uma parede
    LEFT("Left", "Esquerdo"),                                           // Lado esquerdo (relativo a uma orientação)
    RIGHT("Right", "Direito"),                                          // Lado direito (relativo a uma orientação)
    FRONT("Front", "Frente"),                                           // Lado da frente
    BACK("Back", "Trás");                                              // Lado de trás

    private final String descriptionEn;
    private final String descriptionPt;

    SideOfWall(String descriptionEn, String descriptionPt) {
        this.descriptionEn = descriptionEn;
        this.descriptionPt = descriptionPt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionPt() {
        return descriptionPt;
    }

    // Método para obter descrição baseada no Locale (opcional)
    public String getDescription(java.util.Locale locale) {
        if (java.util.Locale.CANADA.getLanguage().equals(locale.getLanguage()) ||
            java.util.Locale.US.getLanguage().equals(locale.getLanguage()) ||
            java.util.Locale.UK.getLanguage().equals(locale.getLanguage())) {
            return descriptionEn;
        }
        return descriptionPt; // Default para Português ou outros
    }
}