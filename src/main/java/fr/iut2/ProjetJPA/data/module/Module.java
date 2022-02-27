package fr.iut2.ProjetJPA.data.module;

import fr.iut2.ProjetJPA.data.exam.Exam;
import fr.iut2.ProjetJPA.data.group.Group;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "followed_modules",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Exam> exams;

    public Module() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        if (!this.groups.contains(group)) {
            this.groups.add(group);
            group.addModule(this);
        }
    }

    public void removeGroup(Group group) {
        if (this.groups.contains(group)) {
            this.groups.remove(group);
            group.removeModule(this);
        }
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void addExam(Exam exam) {
        if (!this.exams.contains(exam)) {
            this.exams.add(exam);
            exam.setModule(this);
        }
    }

    public void removeExam(Exam exam) {
        if (this.exams.contains(exam)) {
            this.exams.remove(exam);
            exam.setModule(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        Module module = (Module) o;
        return Objects.equals(getId(), module.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Module [" + this.getName() + "]";
    }
}
