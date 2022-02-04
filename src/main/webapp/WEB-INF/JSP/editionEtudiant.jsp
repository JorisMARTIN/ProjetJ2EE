<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:useBean id="etudiant" class="fr.iut2.ProjetJPA.data.student.Student" scope="request"/>

<html>
<body>

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

<%-- Formulaire --%>
<form method="post" action="<%= application.getContextPath()%>/do/modifetudiant">

    <input type="hidden" name="id" value="<%=etudiant.getId()%>">

    <label>Moyenne : </label>
    <input type="text" name="moyenne">

    <input type="submit" value="Modifier"/>
</form>

</body>
</html>
