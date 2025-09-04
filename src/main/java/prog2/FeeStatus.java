package hei.prog2;

public enum FeeStatus {
    IN_PROGRESS("en cours"),
    PAID("payé"),
    LATE("en retard"),
    OVERPAID("en surplus"),
    ZERO("nul");

    private final String description;

    FeeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}