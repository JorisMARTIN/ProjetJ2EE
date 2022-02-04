package fr.iut2.ProjetJPA.data.absence;

import fr.iut2.ProjetJPA.data.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Absence() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        if (!this.student.equals(student)) {
            this.student = student;
            if (student != null) student.addAbsence(this);
        }
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
        return "Absence [" + this.getId() + "] Date: " + this.getDate() + " | StudentId: " + this.getStudent().getId();
    }
}
