package fr.iut2.ProjetJPA.data.exam;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.module.Module;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class ExamDAO {

    public static Exam create(String name, Date date, Module module) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Exam exam = new Exam();

        exam.setName(name);
        exam.setDate(date);
        exam.setModule(module);

        em.persist(exam);

        em.getTransaction().commit();
        em.close();

        return exam;
    }

    public static Exam update(Exam exam) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        em.merge(exam);
        em.getTransaction().commit();
        em.close();

        return exam;
    }

    public static void remove(Exam exam) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        if (!em.contains(exam)) {
            exam = em.merge(exam);
        }

        em.remove(exam);

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        int deletedCount = em.createQuery("DELETE FROM Exam").executeUpdate();
        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    public static List<Exam> getAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT e FROM Exam e");

        @SuppressWarnings("unchecked")
        List<Exam> examList = q.getResultList();

        return examList;
    }
}
