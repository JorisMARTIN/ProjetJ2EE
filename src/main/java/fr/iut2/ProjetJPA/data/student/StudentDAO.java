package fr.iut2.ProjetJPA.data.student;


import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.group.Group;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class StudentDAO {

    public static Student findById(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        Student etu = em.find(Student.class, id);
        // etu est maintenant un objet de la classe Etudiant
        // ou NULL si l'étudiant n'existe pas

        // Close the entity manager
        em.close();

        return etu;
    }


    public static Student create(String firstname, String lastname, Group group) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // create new etudiant
        Student etudiant = new Student();
        etudiant.setFirstname(firstname);
        etudiant.setLastname(lastname);
        etudiant.setGroup(group);
        em.persist(etudiant);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return etudiant;
    }

    public static Student update(Student student) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Attacher une entité persistante (etudiant) à l’EntityManager courant  pour réaliser la modification
        em.merge(student);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return student;
    }

    public static void remove(Student student) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // L'étudiant passé en paramètre doit être associé à l'EM
        if (!em.contains(student)) {
            student = em.merge(student);
        }

        // Remove
        em.remove(student);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }

    public static void removeById(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        //
        em.createQuery("DELETE FROM Student AS e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }

    public static int removeAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Student").executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return deletedCount;
    }

    // Retourne l'ensemble des etudiants
    public static List<Student> getAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche 
        Query q = em.createQuery("SELECT e FROM Student e");

        @SuppressWarnings("unchecked")
        List<Student> listEtudiant = q.getResultList();

        return listEtudiant;
    }

}
