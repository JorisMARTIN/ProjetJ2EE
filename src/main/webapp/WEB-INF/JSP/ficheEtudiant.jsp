<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:useBean id="etudiant" class="fr.iut2.ProjetJPA.data.student.Student" scope="request"/>

<div class="card">
    <div class="card-header">Fiche individuelle <small>(ficheEtudiant.jsp)</small></div>
    <div class="card-body">

        <h3>Fiche de <%=etudiant.getFirstname()%> <%=etudiant.getLastname()%>
        </h3>

        <table border="1">
            <tr>
                <td>Groupe</td>
                <td><%=etudiant.getGroup().getName()%>
                </td>
            </tr>
            <tr>
                <td>Moyenne Générale</td>
                <td><%=etudiant.getMoyenne()%></td>
            </tr>
            <tr>
                <td>Absences</td>
                <td><%=etudiant.getNbAbsences()%></td>
            </tr>
        </table>

    </div>
</div>
