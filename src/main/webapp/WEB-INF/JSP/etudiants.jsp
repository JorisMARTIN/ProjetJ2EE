<%@page import="fr.iut2.ProjetJPA.data.student.Student" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiants" type="java.util.List<fr.iut2.ProjetJPA.data.student.Student>" scope="request"/>


<html>
<head>
    <title><%=application.getInitParameter("title")%>
    </title>
</head>
<body>

<h1>Liste des étudiants présents en BD</h1>
<p><a href="<%= application.getContextPath()%>/do/groupes">voir les groupes</a></p>

<!-- tableau d'étudiants  -->
<table>

    <tr>
        <th>Prénom/nom de l'étudiant</th>
        <th>Son groupe</th>
    <tr>


            <% for (Student etudiant : etudiants) {%>

    <tr>
        <td>
            <a href="<%= application.getContextPath()%>/do/ficheetudiant?id=<%=etudiant.getId()%>">
                    <%=etudiant.getFirstname()%> <%=etudiant.getLastname()%>
            </a>
        </td>
        <td><%=etudiant.getGroup().getName()%>
        </td>
    </tr>
    <% } %>
</table>


</body>
</html>
