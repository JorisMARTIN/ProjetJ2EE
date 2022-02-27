package fr.iut2.ProjetJPA.data.module;

import fr.iut2.ProjetJPA.data.GestionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ModuleDAO {

    public static Module findById(int id) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Module module = em.find(Module.class, id);
        em.close();

        return module;
    }

    public static Module findByName(String name) {
        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        List<Module> modules = em.createQuery("SELECT m FROM Module as m WHERE m.name = :name", Module.class)
                .setParameter("name", name)
                .getResultList();

        em.getTransaction().commit();

        em.close();

        return modules.size() > 0 ? modules.get(0) : null;
    }

    public static Module create(String name) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        Module module = new Module();
        module.setName(name);
        em.persist(module);

        em.getTransaction().commit();
        em.close();

        return module;
    }

    public static Module update(Module module) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.merge(module);

        em.getTransaction().commit();
        em.close();

        return module;
    }

    public static void remove(Module module) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        if (!em.contains(module)) {
            module = em.merge(module);
        }

        em.remove(module);
        em.getTransaction().commit();

        em.close();
    }

    public static void removeById(int id) {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("DELETE FROM Module AS m WHERE m.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public static int removeAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        em.getTransaction().begin();

        int deletedCount = em.createQuery("DELETE FROM Module").executeUpdate();

        em.getTransaction().commit();
        em.close();

        return deletedCount;
    }

    public static List<Module> getAll() {

        EntityManager em = GestionFactory.factory.createEntityManager();

        Query q = em.createQuery("SELECT m FROM Module m");

        @SuppressWarnings("unchecked")
        List<Module> listEtudiant = q.getResultList();

        return listEtudiant;
    }

}
