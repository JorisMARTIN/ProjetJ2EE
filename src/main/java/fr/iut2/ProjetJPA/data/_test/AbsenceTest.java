package fr.iut2.ProjetJPA.data._test;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.absence.Absence;
import fr.iut2.ProjetJPA.data.absence.AbsenceDAO;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class AbsenceTest {

    public static void main(String[] args) {
        GestionFactory.open();

        final Group group = GroupDAO.create("AW");
        final Student student = StudentDAO.create("Hervé", "Blanchon", group);

        Timestamp startTime = new Timestamp(new java.util.Date().getTime());
        Timestamp endTime = new Timestamp(startTime.getTime() + 200000);

        Absence absence = AbsenceDAO.create(startTime, endTime, student);
        final int idAbs = absence.getId();

        System.out.print("AbsenceDAO.findById - ");
        absence = AbsenceDAO.findById(absence.getId());
        if (absence != null && absence.getId() == idAbs) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("AbsenceDAO.update - ");
        Timestamp newDate = new Timestamp(new Date(2030, 1,30).getTime());
        absence.setStartTime(newDate);
        Absence updatedAbsence = AbsenceDAO.update(absence);
        if (updatedAbsence.getStartTime().getTime() == newDate.getTime()) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("AbsenceDAO.remove - ");
        AbsenceDAO.remove(absence);
        absence = AbsenceDAO.findById(idAbs);
        if (absence == null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("AbsenceDAO.removeById - ");
        absence = AbsenceDAO.create(startTime, endTime, student);
        AbsenceDAO.removeById(absence.getId());
        absence = AbsenceDAO.findById(idAbs);
        if (absence == null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        AbsenceDAO.create(new Timestamp(newDate.getTime()), new Timestamp(newDate.getTime() + 20000), student);
        AbsenceDAO.create(new Timestamp(new java.util.Date().getTime()), new Timestamp(new java.util.Date().getTime() + 20000), student);

        System.out.print("AbsenceDAO.getAll - ");
        List<Absence> absences = AbsenceDAO.getAll();
        if (absences.size() == 2) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("AbsenceDAO.removeAll - ");
        AbsenceDAO.removeAll();
        absences = AbsenceDAO.getAll();
        if (absences.size() == 0) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        StudentDAO.removeAll();
        GroupDAO.removeAll();

        GestionFactory.close();
    }
}
