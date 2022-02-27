package fr.iut2.ProjetJPA.data.mark;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.exam.Exam;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.student.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MarkDAO {

    public static Mark findByStudentAndExam(Student student, Exam exam) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        List<Mark> marks = em.createQuery("SELECT m FROM Mark as m WHERE m.student = :student AND m.exam = :exam", Mark.class)
                .setParameter("student", student)
                .setParameter("exam", exam)
                .getResultList();

        em.getTransaction().commit();

        em.close();

        return marks.size() > 0 ? marks.get(0) : null;
    }

    public static Mark create(Student student, Exam exam, float value) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Mark mark = new Mark();
        mark.setStudent(student);
        mark.setExam(exam);
        mark.setValue(value);
        em.persist(mark);

        em.getTransaction().commit();
        em.close();

        return mark;
    }

    public static Mark update(Mark mark) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        em.merge(mark);
        em.getTransaction().commit();
        em.close();

        return mark;
    }

    public static void remove(Mark mark) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        if (!em.contains(mark)) {
            mark = em.merge(mark);
        }

        em.remove(mark);

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        int deletedCount = em.createQuery("DELETE FROM Mark").executeUpdate();
        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    public static List<Mark> getAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT m FROM Mark m");

        @SuppressWarnings("unchecked")
        List<Mark> markList = q.getResultList();

        return markList;
    }
}
