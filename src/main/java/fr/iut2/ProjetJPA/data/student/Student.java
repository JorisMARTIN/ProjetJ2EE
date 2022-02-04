package fr.iut2.ProjetJPA.data.student;


import fr.iut2.ProjetJPA.data.absence.Absence;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.mark.Mark;

import javax.persistence.*;
import java.io.Serializable;
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
    @JoinColumn(name = "group_name", nullable = false)
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

    public void setGroup(Group groupe) {
        this.group = groupe;
        groupe.addStudent(this);
    }

    public List<Absence> getAbsences() {
        return this.absences;
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

    public void removeMark(Mark mark) {
        if (this.marks.contains(mark)) {
            this.marks.remove(mark);
            mark.setStudent(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
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
