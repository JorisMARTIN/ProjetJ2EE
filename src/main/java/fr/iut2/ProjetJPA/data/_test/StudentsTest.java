package fr.iut2.ProjetJPA.data._test;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

public class StudentsTest {

    public static void main(String[] args) {

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

        // Retrouver un etudiant par son id
        Student etudiant_TMP = StudentDAO.findById(idFBM);
        System.out.print("StudentDAO.findById : " );
        if (etudiant_TMP != null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        StudentDAO.create("Philippe", "Martin", MIAM);
        StudentDAO.create("Mario", "Cortes-Cornax", MIAM);
        StudentDAO.create("Françoise", "Coat", SIMO);
        StudentDAO.create("Laurent", "Bonnaud", MESSI);
        StudentDAO.create("Sébastien", "Bourdon", MESSI);
    }

}
