package com.bufalari.building.enums;

public enum ReceivableStatus {
    PENDING("Pending", "Pendente"),
    RECEIVED("Received", "Recebido"),
    PARTIALLY_RECEIVED("Partially Received", "Parcialmente Recebido"),
    OVERDUE("Overdue", "Atrasado"),
    IN_DISPUTE("In Dispute", "Em Disputa"),
    CANCELED("Canceled", "Cancelado"),
    WRITTEN_OFF("Written Off", "Baixado"); // Adicionado se usado no DTO

    private final String descriptionEn;
    private final String descriptionPt;

    ReceivableStatus(String en, String pt) { this.descriptionEn = en; this.descriptionPt = pt; }
    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionPt() { return descriptionPt; }
}