package fr.iut2.ProjetJPA.data.exam;

import fr.iut2.ProjetJPA.data.mark.Mark;
import fr.iut2.ProjetJPA.data.module.Module;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "module_name", nullable = false)
    private Module module;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Mark> marks;

    public Exam() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        if (!this.module.equals(module)) {
            this.module = module;
            if (module != null) {
                module.addExam(this);
            }
        }
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void addMark(Mark mark) {
        if (!this.marks.contains(mark)) {
            this.marks.add(mark);
            mark.setExam(this);
        }
    }

    public void removeMark(Mark mark) {
        if (this.marks.contains(mark)) {
            this.marks.remove(mark);
            mark.setExam(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exam)) return false;
        Exam exam = (Exam) o;
        return getId() == exam.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Exam [" + this.getId() + "] Name: " + this.getName() + " | Date: " + this.getDate() + " | ModuleName: " + this.getModule().getName()    ;
    }
}
