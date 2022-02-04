<%@page import="fr.iut2.ProjetJPA.data.group.Group" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="groupes" type="java.util.List<fr.iut2.ProjetJPA.data.group.Group>" scope="request"/>


<html>
<head>
    <title><%=application.getInitParameter("title")%>
    </title>
</head>
<body>

<h1>Liste des groupes présents en BD</h1>
<p><a href="<%= application.getContextPath()%>/do/etudiants">voir les étudiants</a></p>

<!-- tableau de groupes  -->
<table>

    <tr>
        <th>Nom du groupe</th>
        <th>Nombre d'étudiants</th>
    </tr>

    <% for (Group groupe : groupes) {%>

    <tr>
        <td><%=groupe.getName()%>
        </td>
        <td><%=groupe.getStudents().size()%>
        </td>
    </tr>
    <% } %>
</table>


</body>
</html>
