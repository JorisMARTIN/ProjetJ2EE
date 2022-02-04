package fr.iut2.ProjetJPA.data._test;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
public class GroupTest {

    public static void main(String[] args) {
        GestionFactory.open();

        System.out.print("Création d'un groupe  : ");

        final String groupName = "AW";

        Group group = GroupDAO.create(groupName);
        if (group.getName().equals(groupName)) {
            System.out.println("OK - Groupe crée : " + group);
        } else {
            System.err.println("ERREUR : Espéré " + groupName + ", Reçu : " + group.getName());
            return;
        }

        System.out.print("Modification d'un groupe : ");

        final String newGroupName = "SIMO";
        group.setName(newGroupName);
        Group updatedGroup = GroupDAO.update(group);
        if (updatedGroup.getName().equals(newGroupName)) {
            System.out.println("OK - Groupe actualisé");
            System.out.println(updatedGroup);
        } else {
            System.err.println("ERREUR : Espéré " + newGroupName + ", Reçu : " + updatedGroup.getName());
            return;
        }

       /* System.out.print("Supression du groupe créé : ");
        GroupDAO.removeByName(group.getName());
        List<Group> groups = GroupDAO.getAll();
        if (groups.size() == 0) {
            System.out.println("OK\n");
        } else {
            System.err.println("\nERREUR : La suppression du groupe n'as pas fonctionnée");
            return;
        }


        System.out.println("Ajout de plusieurs groupes");
        final String[] names = {"SIMO", "A1", "A2"};
        for (String name : names) {
            GroupDAO.create(name);
        }
        groups = GroupDAO.getAll();
        System.out.print("\tNombre de groupe créés : " + groups.size() + ". Espéré : " + names.length);
        if (groups.size() == names.length) {
            System.out.println(" - OK\n");
        } else {
            System.err.println("\nERREUR : Dans l'ajout de groupes");
            return;
        }




        System.out.println("Suppression de tous les groupes");
        GroupDAO.removeAll();
        groups = GroupDAO.getAll();
        System.out.print("\tNombre de groupe : " + groups.size() + ". Espéré : 0");
        if (groups.size() == 0) {
            System.out.println(" - OK\n");
        } else {
            System.err.println("\nERREUR : Dans la suppression de tous les groupes");
            return;
        }*/


        GestionFactory.close();
    }
}
