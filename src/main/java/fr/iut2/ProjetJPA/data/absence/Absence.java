package fr.iut2.ProjetJPA.data.absence;

import fr.iut2.ProjetJPA.data.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Absence implements Serializable, Comparable<Absence> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Absence() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        if (this.student != null && this.student.equals(student)) return;
            this.student = student;
            student.addAbsence(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Absence)) return false;
        Absence absence = (Absence) o;
        return Objects.equals(getId(), absence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Absence [" + this.getId() + "] Start time : " + this.getStartTime() + ", End time : " + this.getEndTime() + " | StudentId: " + this.getStudent().getId();
    }

    @Override
    public int compareTo(Absence o) {
        return this.getStartTime().compareTo(o.getStartTime());
    }
}
