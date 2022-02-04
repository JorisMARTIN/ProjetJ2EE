package fr.iut2.ProjetJPA.data.group;


import fr.iut2.ProjetJPA.data.module.Module;
import fr.iut2.ProjetJPA.data.student.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "student_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})    // LAZY = fetch when needed, EAGER = fetch immediately
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "followed_modules",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules;


    public Group() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
            student.getGroup().getStudents().remove(student);
            student.setGroup(this);
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        if (!this.modules.contains(module)) {
            this.modules.add(module);
            module.addGroup(this);
        }
    }

    public void removeModule(Module module) {
        if (this.modules.contains(module)) {
            this.modules.remove(module);
            module.removeGroup(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return getName().equals(group.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Group [" + this.getName() + "]";
    }
}
