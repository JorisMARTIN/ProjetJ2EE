package fr.iut2.ProjetJPA.data.group;


import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.exam.Exam;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GroupDAO {

    public static Group findById(int groupId) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Group group = em.find(Group.class, groupId);

        em.close();

        return group;
    }

    public static Group findByName(String name) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        List<Group> groups = em.createQuery("SELECT g FROM student_group as g WHERE g.name = :name", Group.class)
                        .setParameter("name", name)
                        .getResultList();

        em.getTransaction().commit();

        em.close();

        return groups.size() > 0 ? groups.get(0) : null;
    }

    public static Group create(String nom) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Group group = new Group();
        group.setName(nom.toUpperCase());
        em.persist(group);

        em.getTransaction().commit();
        em.close();

        return group;
    }

    public static Group update(Group group) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        Group g = em.merge(group);
        em.getTransaction().commit();
        em.close();

        return g;
    }

    public static void remove(Group groupe) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        if (!em.contains(groupe)) {
            groupe = em.merge(groupe);
        }
        em.remove(groupe);
        em.getTransaction().commit();
        em.close();
    }

    public static void removeById(int groupId) {

        // First remove student from the group
        Group group = GroupDAO.findById(groupId);
        for (Student student : group.getStudents()) {
            group.removeStudent(student);
            StudentDAO.update(student);
        }

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("DELETE FROM student_group AS e WHERE e.id = :id")
                .setParameter("id", groupId)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();
        int deletedCount = em.createQuery("DELETE FROM student_group").executeUpdate();
        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }


    public static List<Group> getAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT g FROM student_group AS g");

        @SuppressWarnings("unchecked")
        List<Group> listGroup = q.getResultList();

        return listGroup;
    }
}
