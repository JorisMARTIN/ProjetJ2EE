package fr.iut2.ProjetJPA.data.mark;

import fr.iut2.ProjetJPA.data.exam.Exam;
import fr.iut2.ProjetJPA.data.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private MarkKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private float value;

    public Mark() {
        super();
    }

    public MarkKey getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) student.addMark(this);
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        if (exam != null) exam.addMark(this);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark mark = (Mark) o;
        return Objects.equals(getId(), mark.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Mark [" + this.getId() + "] : StudentId: " + this.getStudent().getId() + " | ExamId: " + this.getExam().getId() + " | Value: " + this.getValue();
    }
}
