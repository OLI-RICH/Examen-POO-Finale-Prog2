package hei.prog2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FeeTest {
    private Fee fee;
    private Instant now;
    private Instant pastDeadline;
    private Instant futureDeadline;
    private Student student;

    @BeforeEach
    void setUp() {
        now = Instant.now();
        pastDeadline = now.minusSeconds(3600); // 1 hour ago
        futureDeadline = now.plusSeconds(3600); // 1 hour from now

        student = new Student();
        student.setId(1);
        student.setLastName("Doe");
        student.setFirstName("John");
        student.setEntryDateAtHEI(LocalDate.now());

        fee = new Fee();
        fee.setId(1);
        fee.setLabel("Tuition");
        fee.setAmountToPay(1000.0);
        fee.setStudent(student);
    }

    @Test
    void getStatus_Zero() {
        fee.setDeadline(futureDeadline);
        assertEquals(FeeStatus.ZERO, fee.getStatus(now));
    }

    @Test
    void getStatus_InProgress() {
        fee.setDeadline(futureDeadline);
        fee.addPayment(new Payment(1, 500.0, now.minusSeconds(1)));
        assertEquals(FeeStatus.IN_PROGRESS, fee.getStatus(now));
    }

    @Test
    void getStatus_Paid() {
        fee.setDeadline(futureDeadline);
        fee.addPayment(new Payment(1, 1000.0, now.minusSeconds(1)));
        assertEquals(FeeStatus.PAID, fee.getStatus(now));
    }

    @Test
    void getStatus_Late() {
        fee.setDeadline(pastDeadline);
        fee.addPayment(new Payment(1, 500.0, now.minusSeconds(1)));
        assertEquals(FeeStatus.LATE, fee.getStatus(now));
    }

    @Test
    void getStatus_Overpaid() {
        fee.setDeadline(futureDeadline);
        fee.addPayment(new Payment(1, 1200.0, now.minusSeconds(1)));
        assertEquals(FeeStatus.OVERPAID, fee.getStatus(now));
    }

    @Test
    void getStatus_NullInstant_ThrowsException() {
        fee.setDeadline(futureDeadline);
        assertThrows(IllegalArgumentException.class, () -> fee.getStatus(null));
    }

    @Test
    void addPayment_NullPayment_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> fee.addPayment(null));
    }
}