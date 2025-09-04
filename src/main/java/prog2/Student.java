package hei.prog2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String lastName;
    private String firstName;
    private LocalDate entryDateAtHEI;
    private List<Group> groupHistory;

    public Student() {
        this.groupHistory = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getEntryDateAtHEI() {
        return entryDateAtHEI;
    }

    public void setEntryDateAtHEI(LocalDate entryDateAtHEI) {
        this.entryDateAtHEI = entryDateAtHEI;
    }

    public List<Group> getGroupHistory() {
        return groupHistory;
    }

    public void setGroupHistory(List<Group> groupHistory) {
        if (groupHistory == null) throw new IllegalArgumentException("Group history cannot be null");
        this.groupHistory = groupHistory;
    }

    public void addGroup(Group group) {
        if (group == null) throw new IllegalArgumentException("Group cannot be null");
        groupHistory.add(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (lastName != null ? !lastName.equals(student.lastName) : student.lastName != null) return false;
        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        if (entryDateAtHEI != null ? !entryDateAtHEI.equals(student.entryDateAtHEI) : student.entryDateAtHEI != null) return false;
        return groupHistory != null ? groupHistory.equals(student.groupHistory) : student.groupHistory == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (entryDateAtHEI != null ? entryDateAtHEI.hashCode() : 0);
        result = 31 * result + (groupHistory != null ? groupHistory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", entryDateAtHEI=" + entryDateAtHEI +
                ", groupHistory=" + groupHistory +
                '}';
    }
}