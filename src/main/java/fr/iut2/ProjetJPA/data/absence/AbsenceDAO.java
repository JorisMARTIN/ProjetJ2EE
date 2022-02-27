package fr.iut2.ProjetJPA.data.absence;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.student.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class AbsenceDAO {

    public static Absence findById(int absenceId) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Absence abs = em.find(Absence.class, absenceId);

        em.close();

        return abs;
    }

    public static Absence create(Timestamp startTime, Timestamp endTime, Student student) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Absence absence = new Absence();

        absence.setStartTime(startTime);
        absence.setEndTime(endTime);
        absence.setStudent(student);

        em.persist(absence);

        em.getTransaction().commit();
        em.close();

        return absence;
    }

    public static Absence update(Absence absence) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        em.merge(absence);
        em.getTransaction().commit();
        em.close();

        return absence;
    }

    public static void remove(Absence absence) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        if (!em.contains(absence)) {
            absence = em.merge(absence);
        }

        em.remove(absence);

        em.getTransaction().commit();
        em.close();
    }

    public static void removeById(int absenceId) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("DELETE FROM Absence AS a WHERE a.id = :id")
                .setParameter("id", absenceId)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        int deletedCount = em.createQuery("DELETE FROM Absence").executeUpdate();
        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    public static List<Absence> getAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT a FROM Absence a");

        @SuppressWarnings("unchecked")
        List<Absence> absenceList = q.getResultList();
        Collections.sort(absenceList);

        return absenceList;
    }
}
