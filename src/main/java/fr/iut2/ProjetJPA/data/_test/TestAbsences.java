package fr.iut2.ProjetJPA.data._test;


import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

public class TestAbsences {

    /**
     * @param args
     */
    public static void main(String[] args) {

        //
        GestionFactory.open();

        // Initialisation
        // Creation des groupes
        Group MIAM = GroupDAO.create("MIAM");
        Group SIMO = GroupDAO.create("SIMO");
        Group MESSI = GroupDAO.create("MESSI");

        // Initialisation
        // Creation des étudiants
        Student FBM = StudentDAO.create("Francis", "Brunet-Manquat", MIAM);
        int idFBM = FBM.getId();
        StudentDAO.create("Philippe", "Martin", MIAM);
        StudentDAO.create("Mario", "Cortes-Cornax", MIAM);
        StudentDAO.create("Françoise", "Coat", SIMO);
        StudentDAO.create("Laurent", "Bonnaud", MESSI);
        StudentDAO.create("Sébastien", "Bourdon", MESSI);
        Student MG = StudentDAO.create("Mathieu", "Gatumel", SIMO);

        // Retrouver un etudiant par son id
        Student etudiant_TMP = StudentDAO.findById(idFBM);

        // Suppression d'un étudiant
        StudentDAO.remove(MG);
        //EtudiantDAO.remove(MG.getId());

        // Liste des groupes
        System.out.println("Liste des groupes :");
        for (Group groupe : GroupDAO.getAll()) {
            System.out.println(groupe.getName() + " : (" + groupe.getStudents().size() + " étudiant(s))");
        }

        // Liste des étudiants
        System.out.println("\nListe des étudiants :");
        for (Student etu : StudentDAO.getAll()) {
            System.out.println(etu.getId() + " : " + etu.getFirstname() + " " + etu.getLastname() + " " + etu.getGroup().getName());
        }


        // Liste des étudiants par groupe
        for (Group groupe : GroupDAO.getAll()) {

            System.out.println("\nListe des étudiants " + groupe.getName() + " :");
            for (Student etu : groupe.getStudents()) {
                System.out.println(etu.getId() + " : " + etu.getFirstname() + " " + etu.getLastname() + " " + etu.getGroup().getName());
            }
        }

        // Remove un groupe avec les étudiants associés
        GroupDAO.remove(SIMO);

        // Remove entities
        StudentDAO.removeAll();
        GroupDAO.removeAll();

        //
        GestionFactory.close();
    }

}
