package fr.iut2.ProjetJPA.data._test;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;

import java.util.List;

public class GroupTest {

    public static void main(String[] args) {
        GestionFactory.open();


        final String groupName = "AW";

        Group group = GroupDAO.create(groupName);

        System.out.print("GroupDAO.findById - ");
        group = GroupDAO.findById(group.getId());
        if (group != null && group.getName().equalsIgnoreCase(groupName)) {
            System.out.println("OK");
        } else {
            System.err.println("ERREUR");
            return;
        }

        System.out.print("GroupDAO.update - ");

        final String newGroupName = "SIMO";
        group.setName(newGroupName);
        Group updatedGroup = GroupDAO.update(group);
        if (updatedGroup.getName().equals(newGroupName)) {
            System.out.println("OK");
        } else {
            System.err.println("ERREUR");
            return;
        }

        System.out.print("GroupDAO.remove - ");
        GroupDAO.remove(group);
        List<Group> groups = GroupDAO.getAll();
        if (groups.size() == 0) {
            System.out.println("OK");
        } else {
            System.err.println("ERREUR");
            return;
        }

        System.out.print("GroupDAO.removeById - ");
        group = GroupDAO.create(groupName);
        GroupDAO.removeById(group.getId());
        group = GroupDAO.findById(group.getId());
        if (group == null) {
            System.out.println("OK");
        } else {
            System.err.println("ERREUR");
            return;
        }


        final String[] names = {"SIMO", "A1", "A2"};
        for (String name : names) {
            GroupDAO.create(name);
        }
        groups = GroupDAO.getAll();
        System.out.print("GroupDAO.getAll - ");
        if (groups.size() == names.length) {
            System.out.println("OK");
        } else {
            System.err.println("ERREUR");
            return;
        }

        GroupDAO.removeAll();
        groups = GroupDAO.getAll();
        System.out.print("GroupDAO.removeAll - ");
        if (groups.size() == 0) {
            System.out.println("OK");
        } else {
            System.err.println("ERREUR");
            return;
        }

        GestionFactory.close();
    }
}
