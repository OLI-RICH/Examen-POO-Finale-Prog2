package hei.prog2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Fee {
    private int id;
    private String label;
    private double amountToPay;
    private Instant deadline;
    private Student student;
    private List<Payment> payments;

    public Fee() {
        this.payments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        if (amountToPay < 0) throw new IllegalArgumentException("Amount to pay cannot be negative");
        this.amountToPay = amountToPay;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        if (deadline == null) throw new IllegalArgumentException("Deadline cannot be null");
        this.deadline = deadline;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null");
        this.student = student;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        if (payments == null) throw new IllegalArgumentException("Payments list cannot be null");
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        if (payment == null) throw new IllegalArgumentException("Payment cannot be null");
        payments.add(payment);
    }

    public FeeStatus getStatus(Instant t) {
        if (t == null) throw new IllegalArgumentException("Instant cannot be null");
        double totalPaid = payments.stream()
                .filter(p -> p.getPaymentDateTime().isBefore(t) || p.getPaymentDateTime().equals(t))
                .mapToDouble(Payment::getAmount)
                .sum();

        if (totalPaid == 0) {
            return FeeStatus.ZERO;
        }

        boolean deadlinePassed = deadline.isBefore(t);

        if (totalPaid < amountToPay && !deadlinePassed) {
            return FeeStatus.IN_PROGRESS;
        } else if (totalPaid == amountToPay) {
            return FeeStatus.PAID;
        } else if (totalPaid < amountToPay && deadlinePassed) {
            return FeeStatus.LATE;
        } else if (totalPaid > amountToPay) {
            return FeeStatus.OVERPAID;
        }

        return FeeStatus.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fee fee = (Fee) o;

        if (id != fee.id) return false;
        if (Double.compare(fee.amountToPay, amountToPay) != 0) return false;
        if (label != null ? !label.equals(fee.label) : fee.label != null) return false;
        if (deadline != null ? !deadline.equals(fee.deadline) : fee.deadline != null) return false;
        if (student != null ? !student.equals(fee.student) : fee.student != null) return false;
        return payments != null ? payments.equals(fee.payments) : fee.payments == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        temp = Double.doubleToLongBits(amountToPay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", amountToPay=" + amountToPay +
                ", deadline=" + deadline +
                ", student=" + student +
                ", payments=" + payments +
                '}';
    }
}