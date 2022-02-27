<%@ page import="fr.iut2.ProjetJPA.data.student.Student" %>
<%@ page import="fr.iut2.ProjetJPA.data.absence.Absence" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 26/02/2022
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="absences" type="java.util.List<fr.iut2.ProjetJPA.data.absence.Absence>" scope="request" />
<jsp:useBean id="students" type="java.util.List<fr.iut2.ProjetJPA.data.student.Student>" scope="request" />
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - Absences</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card mb-3">
            <div class="card-header">
                <h2>Absences</h2>
            </div>
            <div class="card-body">
                <h3>Liste des absences</h3>
                <% if (absences.size() > 0) { %>
                    <table class="table table-hover">
                        <thead>
                            <th scope="col">Étudiant</th>
                            <th scope="col">Début d'absence</th>
                            <th scope="col">Fin d'absence</th>
                            <th scope="col"></th>
                        </thead>
                        <tbody>
                            <% for (Absence abs : absences) { %>
                                <tr>
                                    <td><%=abs.getStudent().getName()%></td>
                                    <td><%=abs.getStartTime()%></td>
                                    <td><%=abs.getEndTime()%></td>
                                    <td><a class="btn btn-danger" href="<%=application.getContextPath()%>/do/deleteAbsence?id=<%=abs.getId()%>">Supprimer</a></td>
                                </tr>
                            <% } %>

                        </tbody>
                    </table>
                <% } else { %>
                    <span>Aucunes absences disponibles.</span>
                <% } %>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                <h2>Ajouter une absence</h2>
            </div>
            <div class="card-body">
                <form method="POST" action="<%=application.getContextPath()%>/do/newAbsence">
                    <div class="row">
                        <div class="col-5">
                            <label for="studentSelect" class="form-label">Étudiant</label>
                            <select class="form-control" id="studentSelect" aria-label="Student select" name="studentId" required>
                                <% for (Student student : students) { %>
                                    <option value="<%=student.getId()%>"><%=student.getName()%> [<%= student.getGroup().getName()%>]</option>
                                <% } %>
                            </select>
                        </div>

                        <div class="col-5">
                            <div>
                                <label for="startTimeInput" class="form-label">Date début</label>
                                <input type="datetime-local" id="startTimeInput" name="startTime" class="form-control" required>
                            </div>
                            <div>
                                <label for="endTimeInput" class="form-label">Date de fin</label>
                                <input type="datetime-local" id="endTimeInput" name="endTime" class="form-control" required>
                            </div>
                        </div>

                        <button type="submit" class="col-2 btn btn-primary">Soumettre</button>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
