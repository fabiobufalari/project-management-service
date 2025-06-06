package com.bufalari.building.enums;

public enum PayableStatus {
    PENDING("Pending", "Pendente"),
    PAID("Paid", "Pago"),
    PARTIALLY_PAID("Partially Paid", "Parcialmente Pago"),
    OVERDUE("Overdue", "Atrasado"),
    CANCELED("Canceled", "Cancelado"),
    IN_NEGOTIATION("In Negotiation", "Em Negociação"); // Adicionado se usado no DTO

    private final String descriptionEn;
    private final String descriptionPt;

    PayableStatus(String en, String pt) { this.descriptionEn = en; this.descriptionPt = pt; }
    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionPt() { return descriptionPt; }
}
