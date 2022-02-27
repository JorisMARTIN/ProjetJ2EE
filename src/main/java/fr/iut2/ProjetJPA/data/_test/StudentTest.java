package fr.iut2.ProjetJPA.data._test;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

import java.util.List;

public class StudentTest {

    public static void main(String[] args) {

        GestionFactory.open();

        // Initialisation
        // Creation des groupes
        Group MIAM = GroupDAO.create("MIAM");

        // Initialisation
        // Creation des étudiants
        Student FBM = StudentDAO.create("Francis", "Brunet-Manquat", MIAM);
        int idFBM = FBM.getId();

        // Retrouver un etudiant par son id
        Student etudiant_TMP = StudentDAO.findById(idFBM);
        System.out.print("StudentDAO.findById : " );
        if (etudiant_TMP != null && etudiant_TMP.getId() == idFBM) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        final String newName = "Francis le 100";

        System.out.print("StudentDAO.update : " );
        etudiant_TMP.setFirstname(newName);
        StudentDAO.update(etudiant_TMP);
        etudiant_TMP = StudentDAO.findById(idFBM);
        if (etudiant_TMP.getFirstname().equalsIgnoreCase(newName)) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("StudentDAO.remove : " );
        StudentDAO.remove(etudiant_TMP);
        etudiant_TMP = StudentDAO.findById(idFBM);
        if (etudiant_TMP == null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        FBM = StudentDAO.create("Francis", "Brunet-Manquat", MIAM);
        idFBM = FBM.getId();

        System.out.print("StudentDAO.removeById : " );
        StudentDAO.removeById(idFBM);
        etudiant_TMP = StudentDAO.findById(idFBM);
        if (etudiant_TMP == null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        StudentDAO.create("Francis", "Brunet-Manquat", MIAM);
        StudentDAO.create("Hervé", "Blanchon", MIAM);

        System.out.print("StudentDAO.getAll : " );
        List<Student> sudents_TMP = StudentDAO.getAll();
        if (sudents_TMP.size() == 2) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("StudentDAO.removeAll : " );
        StudentDAO.removeAll();
        if(StudentDAO.getAll().size() == 0) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        GroupDAO.removeAll();

        GestionFactory.close();
    }

}
