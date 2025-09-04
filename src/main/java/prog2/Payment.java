package hei.prog2;

import java.time.Instant;

public class Payment {
    private int id;
    private double amount;
    private Instant paymentDateTime;

    public Payment(int id, double amount, Instant paymentDateTime) {
        if (amount < 0) throw new IllegalArgumentException("Payment amount cannot be negative");
        if (paymentDateTime == null) throw new IllegalArgumentException("Payment date cannot be null");
        this.id = id;
        this.amount = amount;
        this.paymentDateTime = paymentDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Payment amount cannot be negative");
        this.amount = amount;
    }

    public Instant getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(Instant paymentDateTime) {
        if (paymentDateTime == null) throw new IllegalArgumentException("Payment date cannot be null");
        this.paymentDateTime = paymentDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (id != payment.id) return false;
        if (Double.compare(payment.amount, amount) != 0) return false;
        return paymentDateTime != null ? paymentDateTime.equals(payment.paymentDateTime) : payment.paymentDateTime == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (paymentDateTime != null ? paymentDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", paymentDateTime=" + paymentDateTime +
                '}';
    }
}