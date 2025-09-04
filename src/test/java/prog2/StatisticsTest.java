package hei.prog2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    private Student student;
    private Fee fee1;
    private Fee fee2;
    private Instant now;
    private Instant pastDeadline;
    private Instant futureDeadline;

    @BeforeEach
    void setUp() {
        now = Instant.now();
        pastDeadline = now.minusSeconds(3600);
        futureDeadline = now.plusSeconds(3600);

        student = new Student();
        student.setId(1);
        student.setLastName("Doe");
        student.setFirstName("John");
        student.setEntryDateAtHEI(LocalDate.now());

        fee1 = new Fee();
        fee1.setId(1);
        fee1.setLabel("Tuition1");
        fee1.setAmountToPay(1000.0);
        fee1.setDeadline(pastDeadline);
        fee1.setStudent(student);
        fee1.addPayment(new Payment(1, 500.0, now.minusSeconds(7200))); // Late

        fee2 = new Fee();
        fee2.setId(2);
        fee2.setLabel("Tuition2");
        fee2.setAmountToPay(2000.0);
        fee2.setDeadline(futureDeadline);
        fee2.setStudent(student);
        fee2.addPayment(new Payment(2, 2000.0, now.minusSeconds(1))); // Paid
    }

    @Test
    void getLateFees() {
        List<Fee> fees = Arrays.asList(fee1, fee2);
        List<Fee> lateFees = Statistics.getLateFees(fees, now);
        assertEquals(1, lateFees.size());
        assertEquals(fee1, lateFees.get(0));
    }

    @Test
    void getLateFees_EmptyList() {
        List<Fee> fees = Collections.emptyList();
        List<Fee> lateFees = Statistics.getLateFees(fees, now);
        assertTrue(lateFees.isEmpty());
    }

    @Test
    void getLateFees_NullList_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Statistics.getLateFees(null, now));
    }

    @Test
    void getTotalMissingFees() {
        List<Fee> fees = Arrays.asList(fee1, fee2);
        double totalMissing = Statistics.getTotalMissingFees(fees, now);
        assertEquals(500.0, totalMissing, 0.001);
    }

    @Test
    void getTotalMissingFees_EmptyList() {
        List<Fee> fees = Collections.emptyList();
        double totalMissing = Statistics.getTotalMissingFees(fees, now);
        assertEquals(0.0, totalMissing, 0.001);
    }

    @Test
    void getTotalMissingFees_NullList_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Statistics.getTotalMissingFees(null, now));
    }

    @Test
    void getTotalPaidByStudent() {
        List<Fee> fees = Arrays.asList(fee1, fee2);
        double totalPaid = Statistics.getTotalPaidByStudent(student, fees, now);
        assertEquals(2500.0, totalPaid, 0.001);
    }

    @Test
    void getTotalPaidByStudent_NoFeesForStudent() {
        Student otherStudent = new Student();
        otherStudent.setId(2);
        List<Fee> fees = Arrays.asList(fee1, fee2);
        double totalPaid = Statistics.getTotalPaidByStudent(otherStudent, fees, now);
        assertEquals(0.0, totalPaid, 0.001);
    }

    @Test
    void getTotalPaidByStudent_NullStudent_ThrowsException() {
        List<Fee> fees = Arrays.asList(fee1, fee2);
        assertThrows(IllegalArgumentException.class, () -> Statistics.getTotalPaidByStudent(null, fees, now));
    }
}