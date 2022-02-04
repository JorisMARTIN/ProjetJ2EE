package fr.iut2.ProjetJPA.data.mark;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MarkKey implements Serializable {

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "exam_id")
    private Integer examId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer student_id) {
        this.studentId = student_id;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer exam_id) {
        this.examId = exam_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkKey)) return false;
        MarkKey markKey = (MarkKey) o;
        return Objects.equals(getStudentId(), markKey.getStudentId()) && Objects.equals(getExamId(), markKey.getExamId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getExamId());
    }

    @Override
    public String toString() {
        return "[" + this.getStudentId() + ", " + this.getExamId() + "]";
    }
}
