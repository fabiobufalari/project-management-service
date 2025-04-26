// Path: accounts-payable-service/src/main/java/com/bufalari/payable/enums/PayableStatus.java
package com.bufalari.building.payable.enums;

public enum PayableStatus {
    PENDING("Pending", "Pendente"),
    PAID("Paid", "Pago"),
    PARTIALLY_PAID("Partially Paid", "Parcialmente Pago"),
    OVERDUE("Overdue", "Atrasado"),
    CANCELED("Canceled", "Cancelado");

    private final String descriptionEn;
    private final String descriptionPt;

    PayableStatus(String en, String pt) { this.descriptionEn = en; this.descriptionPt = pt; }
    public String getDescriptionEn() { return descriptionEn; }
    public String getDescriptionPt() { return descriptionPt; }
}