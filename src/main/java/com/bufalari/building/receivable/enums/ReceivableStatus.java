package com.bufalari.building.receivable.enums;

public enum ReceivableStatus {
    PENDING("Pending", "Pendente"),
    RECEIVED("Received", "Recebido"),
    PARTIALLY_RECEIVED("Partially Received", "Parcialmente Recebido"),
    OVERDUE("Overdue", "Atrasado"),
    IN_DISPUTE("In Dispute", "Em Disputa"),
    CANCELED("Canceled", "Cancelado"); // Or WRITTEN_OFF / Baixado

    private final String descriptionEn;
    private final String descriptionPt;

    ReceivableStatus(String en, String pt) { this.descriptionEn = en; this.descriptionPt = pt; }
    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionPt() { return descriptionPt; }
}