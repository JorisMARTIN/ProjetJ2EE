package fr.iut2.ProjetJPA.data.student;


import fr.iut2.ProjetJPA.data.absence.Absence;
import fr.iut2.ProjetJPA.data.exam.Exam;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.mark.Mark;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})    // LAZY = fetch when needed, EAGER = fetch immediately
    private List<Absence> absences;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Mark> marks;

    public Student() {
        super();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return this.firstname + ' ' + this.lastname.toUpperCase();
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        if (!Objects.equals(this.group, group)) {
            this.group = group;
            group.addStudent(this);
        }
    }

    public List<Absence> getAbsences() {
        List<Absence> abs = absences;
        Collections.sort(abs);
        return abs;
    }

    public void addAbsence(Absence absence) {
        if (!this.absences.contains(absence)) {
            this.absences.add(absence);
            absence.setStudent(this);
        }
    }

    public void removeAbsence(Absence absence) {
        if (this.absences.contains(absence)) {
            this.absences.remove(absence);
            absence.setStudent(null);
        }
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void addMark(Mark mark) {
        if (!this.marks.contains(mark)) {
            this.marks.add(mark);
            mark.setStudent(this);
        }
    }

    public Mark getMarkFromExam(Exam exam) {
        for (Mark mark : this.getMarks()) {
            if (mark.getExam().getId() == exam.getId()) return mark;
        }

        return null;
    }

    public void removeMark(Mark mark) {
        if (this.marks.contains(mark)) {
            this.marks.remove(mark);
            mark.setStudent(null);
        }
    }

    /**
     * Collect student marks average
     * @return The ponterate average of all marks
     */
    public float getAverage() {
        float average = 0;
        int sumCoef = 0;

        for (Mark mark : marks) {
            average += mark.getValue();
            sumCoef += mark.getExam().getCoeficient();
        }

        return average / sumCoef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getId().equals(student.getId()) && getFirstname().equals(student.getFirstname()) && getLastname().equals(student.getLastname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Student [" + this.getId() + "] " + this.getName();
    }
}
