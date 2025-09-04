package hei.prog2;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Statistics {

    public static List<Fee> getLateFees(List<Fee> fees, Instant t) {
        if (fees == null || t == null) {
            throw new IllegalArgumentException("Fees and instant cannot be null");
        }
        return fees.stream()
                .filter(fee -> fee.getStatus(t) == FeeStatus.LATE)
                .collect(Collectors.toList());
    }

    public static double getTotalMissingFees(List<Fee> fees, Instant t) {
        if (fees == null || t == null) {
            throw new IllegalArgumentException("Fees and instant cannot be null");
        }
        double sum = fees.stream()
                .filter(fee -> fee.getStatus(t) == FeeStatus.LATE)
                .mapToDouble(fee -> fee.getAmountToPay() - fee.getPayments().stream()
                        .filter(p -> p.getPaymentDateTime().isBefore(t) || p.getPaymentDateTime().equals(t))
                        .mapToDouble(Payment::getAmount)
                        .sum())
                .sum();
        return sum;
    }

    public static double getTotalPaidByStudent(hei.prog2.Student student, List<Fee> fees, Instant t) {
        if (student == null || fees == null || t == null) {
            throw new IllegalArgumentException("Student, fees, and instant cannot be null");
        }
        return fees.stream()
                .filter(fee -> fee.getStudent().equals(student))
                .flatMap(fee -> fee.getPayments().stream())
                .filter(p -> p.getPaymentDateTime().isBefore(t) || p.getPaymentDateTime().equals(t))
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}