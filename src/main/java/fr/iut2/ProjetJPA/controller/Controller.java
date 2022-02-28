/**
 * @author Joris MARTIN
 */

// Numéro 63

package fr.iut2.ProjetJPA.controller;

import fr.iut2.ProjetJPA.data.*;
import fr.iut2.ProjetJPA.data.absence.Absence;
import fr.iut2.ProjetJPA.data.absence.AbsenceDAO;
import fr.iut2.ProjetJPA.data.exam.Exam;
import fr.iut2.ProjetJPA.data.exam.ExamDAO;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.mark.Mark;
import fr.iut2.ProjetJPA.data.mark.MarkDAO;
import fr.iut2.ProjetJPA.data.module.Module;
import fr.iut2.ProjetJPA.data.module.ModuleDAO;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {

    private String
            urlIndex,
            urlStudents,
            urlStudent,
            urlGroups,
            urlAbsences,
            urlModules,
            urlModule,
            urlExams,
            urlMarks;

    // INIT
    @Override
    public void init() throws ServletException {


        // Récupération des URLs en paramètre du web.xml
        urlIndex = getServletConfig().getInitParameter("indexUrl");
        urlStudents = getServletConfig().getInitParameter("studentsUrl");
        urlStudent = getServletConfig().getInitParameter("studentDetailUrl");
        urlGroups = getServletConfig().getInitParameter("groupsUrl");
        urlAbsences = getServletConfig().getInitParameter("absencesUrl");
        urlModules = getServletConfig().getInitParameter("modulesUrl");
        urlModule = getServletConfig().getInitParameter("moduleDetailUrl");
        urlExams = getServletConfig().getInitParameter("examsUrl");
        urlMarks = getServletConfig().getInitParameter("marksUrl");
        // Création de la factory permettant la création d'EntityManager
        // (gestion des transactions)
        GestionFactory.open();

        ///// INITIALISATION DE LA BD
        // Normalement l'initialisation se fait directement dans la base de données
        if ((GroupDAO.getAll().size() == 0) && (StudentDAO.getAll().size() == 0)) {

            // Creation des groupes
            Group AW = GroupDAO.create("AW");
            Group SIMO = GroupDAO.create("SIMO");

            // Creation des étudiants
            StudentDAO.create("Francis", "Brunet-Manquat", AW);
            StudentDAO.create("Philippe", "Martin", AW);
            StudentDAO.create("Mario", "Cortes-Cornax", SIMO);
            StudentDAO.create("Françoise", "Coat", SIMO);
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        // Fermeture de la factory
        GestionFactory.close();
    }

    // POST
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getPathInfo();

        switch (action) {

            case "/newGroup":
                doNewGroup(request, response);
                break;

            case "/newStudent":
                doNewStudent(request, response);
                break;

            case "/updateStudent":
                doUpdateStudent(request, response);
                break;

            case "/newAbsence":
                doNewAbsence(request, response);
                break;

            case "/newModule":
                doNewModule(request, response);
                break;

            case "/updateModule":
                doUpdateModule(request, response);
                break;

            case "/newExam":
                doNewExam(request, response);
                break;

            case "/saveMarks":
                doSaveMarks(request, response);
                break;

            default:
                doIndex(request, response);
        }
    }

    // GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // On récupère le path
        String action = request.getPathInfo();
        if (action == null) {
            action = "/index";
        }

        switch (action) {

            case "/students":
                doStudents(request, response);
                break;

            case "/groups":
                doGroups(request, response);
                break;

            case "/deleteGroup":
                doDeleteGroup(request, response);
                break;

            case "/student":
                doStudent(request, response);
                break;

            case "/absences":
                doAbsences(request, response);
                break;

            case "/deleteAbsence":
                doDeleteAbsence(request, response);
                break;

            case "/modules":
                doModules(request, response);
                break;

            case "/module":
                doModule(request, response);
                break;

            case "/deleteModule":
                doDeleteModule(request, response);
                break;

            case "/exams":
                doExams(request, response);
                break;

            case "/marks":
                doMarks(request, response);
                break;

            default:
                doIndex(request, response);
        }
    }



    private void doIndex (HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        loadJSP(urlIndex, request, response);
    }

    private void doStudents(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {

        List<Student> allStudents = StudentDAO.getAll();
        List<Group> groups = GroupDAO.getAll();

        request.setAttribute("students", allStudents);
        request.setAttribute("groups", groups);

        loadJSP(urlStudents, request, response);
    }

    private void doStudent(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("id"));
        Student student = StudentDAO.findById(studentId);
        List<Group> groups = GroupDAO.getAll();
        List<Module> modules = student.getGroup().getModules();

        request.setAttribute("student", student);
        request.setAttribute("groups", groups);

        Map<Module, Float> modulesAverage = new HashMap<>();

        float globalAverage = 0.0f;
        int computedModule = 0;

        for (Module module : modules) {
            float moduleAverage = 0.0f;
            int moduleCoef = 0;
            for (Exam exam : module.getExams()) {
                Mark studentMark = student.getMarkFromExam(exam);
                if (studentMark != null)  {
                    moduleAverage += studentMark.getValue() * exam.getCoeficient();
                    moduleCoef += exam.getCoeficient();
                }
            }

            if (moduleAverage > 0) {
                moduleAverage = moduleAverage / moduleCoef;

                modulesAverage.put(module, Math.round(moduleAverage * 100.0f) / 100.0f);
                globalAverage += moduleAverage;
                computedModule ++;
            } else {
                modulesAverage.put(module, -1.0f);
            }
        }

        globalAverage = globalAverage / computedModule;

        request.setAttribute("modules", modulesAverage);
        request.setAttribute("globalAverage", Math.round(globalAverage * 100.0f) / 100.0f);

        loadJSP(urlStudent, request, response);
    }

    private void doNewStudent(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        int groupId = Integer.parseInt(request.getParameter("groupId"));

        Group group = GroupDAO.findById(groupId);

        Student student = StudentDAO.create(firstname, lastname, group);

        response.sendRedirect(request.getContextPath() + "/do/student?id=" + student.getId());
    }

    private void doUpdateStudent(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Student student = StudentDAO.findById(studentId);

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        Group group = GroupDAO.findById(groupId);

        if(!firstname.equalsIgnoreCase(student.getFirstname())) student.setFirstname(firstname);
        if(!lastname.equalsIgnoreCase(student.getLastname())) student.setLastname(lastname);
        if(group.getId() != student.getGroup().getId()) student.setGroup(group);

        StudentDAO.update(student);

        response.sendRedirect(request.getContextPath() + "/do/student?id=" + student.getId());
    }

    private void doGroups(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {

        List<Group> allGroups = GroupDAO.getAll();

        request.setAttribute("groups", allGroups);

        loadJSP(urlGroups, request, response);
    }

    private void doNewGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String groupName = request.getParameter("groupName");
        Group group = GroupDAO.findByName(groupName.toUpperCase());
        if (group == null) {
            GroupDAO.create(groupName);
        }

        response.sendRedirect(request.getContextPath() + "/do/groups");
    }

    private void doDeleteGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int groupId = Integer.parseInt(request.getParameter("id"));
        Group group = GroupDAO.findById(groupId);

        for (int i = 0; i < group.getModules().size(); i++) {
            group.removeModule(group.getModules().get(i));
        }

        GroupDAO.update(group);
        GroupDAO.remove(group);

        response.sendRedirect(request.getContextPath() + "/do/groups");
    }

    private void doAbsences(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Absence> absences = AbsenceDAO.getAll();
        List<Student> students = StudentDAO.getAll();

        request.setAttribute("absences", absences);
        request.setAttribute("students", students);

        loadJSP(urlAbsences, request, response);
    }

    private void doNewAbsence(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        Student student = StudentDAO.findById(studentId);
        Timestamp startTime = Timestamp.valueOf(request.getParameter("startTime").replace("T", " ") + ":00");
        Timestamp endTime = Timestamp.valueOf(request.getParameter("endTime").replace("T", " ") + ":00");


        /*
         * Vérification de la validitées des dates
         *
         *  Invalidité de chevauchement
         *  |----------abs.startTime-------startTime-------abs.endTime-----endTime---|
         *  ou
         *  |----------startTime-----abs.startTime-----endTime-----abs.endTime----|
         *
         *  Invalidité d'encapsulation
         *  |----------abs.startTime-----startTime-----endTime-----abs.endTime----|
         *
         */
        for (Absence abs : student.getAbsences()) {
            if (
                    // Vérification chevauchement
                    (abs.getStartTime().compareTo(startTime) < 0 && startTime.compareTo(abs.getEndTime()) < 0)
                    ||
                    (startTime.compareTo(abs.getStartTime()) < 0 && abs.getStartTime().compareTo(endTime) < 0)
                    ||
                    // Vérification d'encapsulation
                    (abs.getStartTime().compareTo(startTime) < 0 && endTime.compareTo(abs.getEndTime()) < 0)
            ) response.sendRedirect(request.getContextPath() + "/do/absences");

        }

        // Les dates sont valides on peut donc créer
        AbsenceDAO.create(startTime, endTime, student);

        response.sendRedirect(request.getContextPath() + "/do/absences");
    }

    private void doDeleteAbsence(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int absenceId = Integer.parseInt(request.getParameter("id"));

        AbsenceDAO.removeById(absenceId);

        response.sendRedirect(request.getContextPath() + "/do/absences");
    }

    private void doModules(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Module> modules = ModuleDAO.getAll();

        request.setAttribute("modules", modules);

        loadJSP(urlModules, request, response);
    }

    private void doModule(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int moduleId = Integer.parseInt(request.getParameter("id"));

        Module module = ModuleDAO.findById(moduleId);
        List<Group> groups = GroupDAO.getAll();

        request.setAttribute("module", module);
        request.setAttribute("groups", groups);

        loadJSP(urlModule, request, response);
    }

    private void doNewModule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        if (ModuleDAO.findByName(name) == null) {
            Module module = ModuleDAO.create(name);

            response.sendRedirect(request.getContextPath() + "/do/module?id=" + module.getId());
        } else {
            response.sendRedirect(request.getContextPath() + "/do/modules");
        }
    }

    private void doUpdateModule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Module module = ModuleDAO.findById(Integer.parseInt(request.getParameter("moduleId")));
        String name = request.getParameter("name");

        for (Group group : GroupDAO.getAll()) {
            if (request.getParameter("groupId-" + group.getId()) != null) {
                if (!module.getGroups().contains(group)) module.addGroup(group);
            } else {
                if (module.getGroups().contains(group)) module.removeGroup(group);
            }
        }

        module.setName(name);

        ModuleDAO.update(module);

        response.sendRedirect(request.getContextPath() + "/do/module?id=" + module.getId());
    }

    private void doDeleteModule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int moduleId = Integer.parseInt(request.getParameter("id"));

        Module module = ModuleDAO.findById(moduleId);

        for (int i = 0; i < module.getGroups().size(); i++) {
            module.removeGroup(module.getGroups().get(i));
        }

        ModuleDAO.update(module);
        ModuleDAO.remove(module);

        response.sendRedirect(request.getContextPath() + "/do/modules");
    }

    private void doExams(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Module> modules = ModuleDAO.getAll();

        request.setAttribute("modules", modules);

        loadJSP(urlExams, request, response);
    }

    private void doNewExam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int moduleId = Integer.parseInt(request.getParameter("moduleId"));
        Module module = ModuleDAO.findById(moduleId);

        if (module != null) {
            String name = request.getParameter("name");
            Timestamp date = Timestamp.valueOf(request.getParameter("date").replace("T", " ") + ":00");
            int coef = Integer.parseInt(request.getParameter("coef"));

            ExamDAO.create(name, date, coef, module);
        }

        response.sendRedirect(request.getContextPath() + "/do/exams");
    }

    private void doMarks(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        Exam exam = ExamDAO.findById(examId);

        Module module = exam.getModule();
        List<Group> groups = module.getGroups();
        List<Mark> marks = exam.getMarks();

        request.setAttribute("groups", groups);
        request.setAttribute("marks", marks);
        request.setAttribute("exam", exam);

        Map<Integer, Float> examAverageByGroup = new HashMap<>();
        for (Group group : groups) {
            float average = 0;
            List<Mark> groupMarks = exam.getGroupMarks(group);
            if (groupMarks.size() > 0) {
                for (Mark mark : groupMarks) {
                    average += mark.getValue();
                }
                average = average / groupMarks.size();
            }
            examAverageByGroup.put(group.getId(), average);
        }

        request.setAttribute("averages", examAverageByGroup);

        loadJSP(urlMarks, request, response);
    }

    private void doSaveMarks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        Exam exam = ExamDAO.findById(examId);

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        Group group = GroupDAO.findById(groupId);

        for (Student student : group.getStudents()) {
            float markValue = Float.parseFloat(request.getParameter("student-" + student.getId()));
            Mark existingMark = MarkDAO.findByStudentAndExam(student, exam);

            if (existingMark != null) {
                existingMark.setValue(markValue);
                MarkDAO.update(existingMark);
            } else {
                MarkDAO.create(student, exam, markValue);
            }
        }

        response.sendRedirect(request.getContextPath() + "/do/marks?examId=" + exam.getId());
    }

    /**
     * Charge la JSP indiquée en paramètre
     *
     * @param url
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loadJSP(String url, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // L'interface RequestDispatcher permet de transférer le contrôle à une
        // autre servlet
        // Deux méthodes possibles :
        // - forward() : donne le contrôle à une autre servlet. Annule le flux
        // de sortie de la servlet courante
        // - include() : inclus dynamiquement une autre servlet
        // + le contrôle est donné à une autre servlet puis revient à la servlet
        // courante (sorte d'appel de fonction).
        // + Le flux de sortie n'est pas supprimé et les deux se cumulent

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

}
