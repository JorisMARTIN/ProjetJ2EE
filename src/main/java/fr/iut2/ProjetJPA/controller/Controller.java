/**
 * @author hb
 */

// Numéro 63

package fr.iut2.ProjetJPA.controller;

import fr.iut2.ProjetJPA.data.*;
import fr.iut2.ProjetJPA.data.group.Group;
import fr.iut2.ProjetJPA.data.group.GroupDAO;
import fr.iut2.ProjetJPA.data.student.Student;
import fr.iut2.ProjetJPA.data.student.StudentDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {

    private String urlEtudiants;
    private String urlGroupes;
    private String urlFicheEtudiant;
    private String urlEditionEtudiant;

    // INIT
    @Override
    public void init() throws ServletException {


        // Récupération des URLs en paramètre du web.xml
        urlEtudiants = getServletConfig().getInitParameter("urlEtudiants");
        urlGroupes = getServletConfig().getInitParameter("urlGroupes");
        urlFicheEtudiant = getServletConfig().getInitParameter("urlFicheEtudiant");
        urlEditionEtudiant = getServletConfig().getInitParameter("urlEditionEtudiant");

        // Création de la factory permettant la création d'EntityManager
        // (gestion des transactions)
        GestionFactory.open();

        ///// INITIALISATION DE LA BD
        // Normalement l'initialisation se fait directement dans la base de données
        if ((GroupDAO.getAll().size() == 0) && (StudentDAO.getAll().size() == 0)) {

            // Creation des groupes
            Group MIAM = GroupDAO.create("miam");
            Group SIMO = GroupDAO.create("SIMO");
            Group MESSI = GroupDAO.create("MESSI");

            // Creation des étudiants
            StudentDAO.create("Francis", "Brunet-Manquat", MIAM);
            StudentDAO.create("Philippe", "Martin", MIAM);
            StudentDAO.create("Mario", "Cortes-Cornax", MIAM);
            StudentDAO.create("Françoise", "Coat", SIMO);
            StudentDAO.create("Laurent", "Bonnaud", MESSI);
            StudentDAO.create("Sébastien", "Bourdon", MESSI);
            StudentDAO.create("Mathieu", "Gatumel", SIMO);
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
        // on passe la main au GET
        doGet(request, response);
    }

    // GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // On récupère le path
        String action = request.getPathInfo();
        if (action == null) {
            action = "/etudiants";
        }

        // Log action
        System.out.println("PROJET JPA : action = " + action);

        // Exécution action
        if (action.equals("/etudiants")) {

            doEtudiants(request, response);

        } else if (action.equals("/groupes")) {
            doGroupes(request, response);

        } else if (action.equals("/ficheetudiant")) {
            doFicheEtudiant(request, response);

        } else if (action.equals("/editionetudiant")) {
            doEditionEtudiant(request, response);

        } else if (action.equals("/modifetudiant")) {
            doModifEtudiant(request, response);

        } else {
            // Autres cas
            doEtudiants(request, response);
        }
    }

    // ///////////////////////
    //
    private void doEtudiants(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer les étudiants
        List<Student> etudiants = StudentDAO.getAll();

        // Ajouter les étudiants à la requête pour affichage
        request.setAttribute("etudiants", etudiants);

        //
        loadJSP(urlEtudiants, request, response);
    }

    // ///////////////////////
    //
    private void doGroupes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer les étudiants
        List<Group> groupes = GroupDAO.getAll();

        // Ajouter les étudiants à la requête pour affichage
        request.setAttribute("groupes", groupes);

        //
        loadJSP(urlGroupes, request, response);
    }

    // ///////////////////////
    //
    private void doFicheEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer l'id de l'étudiant
        int idEtudiant = Integer.valueOf(request.getParameter("id"));

        // Récupérer l'étudiant
        Student etudiant = StudentDAO.findById(idEtudiant);

        // Ajouter l'étudiant à la requête pour affichage
        request.setAttribute("etudiant", etudiant);

        //
        loadJSP(urlFicheEtudiant, request, response);
    }

    // ///////////////////////
    //
    private void doEditionEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer l'id de l'étudiant
        int idEtudiant = Integer.valueOf(request.getParameter("id"));

        // Récupérer l'étudiant
        Student etudiant = StudentDAO.findById(idEtudiant);

        // Ajouter l'étudiant à la requête pour affichage
        request.setAttribute("etudiant", etudiant);

        //
        loadJSP(urlEditionEtudiant, request, response);
    }


    // ///////////////////////
    //
    private void doModifEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer l'id de l'étudiant
        int idEtudiant = Integer.valueOf(request.getParameter("id"));
        int moyenneEtudiant = Integer.valueOf(request.getParameter("moyenne"));

        // Récupérer l'étudiant
        Student etudiant = StudentDAO.findById(idEtudiant);

        // Modifier l'étudiant
//        etudiant.setMoyenne(moyenneEtudiant);
        StudentDAO.update(etudiant);

        // Ajouter l'étudiant à la requête pour affichage
        request.setAttribute("etudiant", etudiant);

        //
        loadJSP(urlFicheEtudiant, request, response);
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
