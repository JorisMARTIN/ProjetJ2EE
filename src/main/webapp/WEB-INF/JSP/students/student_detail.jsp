<%@ page import="fr.iut2.ProjetJPA.data.absence.Absence" %>
<%@ page import="fr.iut2.ProjetJPA.data.mark.Mark" %>
<%@ page import="fr.iut2.ProjetJPA.data.group.Group" %>
<%@ page import="fr.iut2.ProjetJPA.data.module.Module" %>
<%@ page import="fr.iut2.ProjetJPA.data.exam.Exam" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 21/02/2022
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="student" type="fr.iut2.ProjetJPA.data.student.Student" scope="request"/>
<jsp:useBean id="groups" type="java.util.List<fr.iut2.ProjetJPA.data.group.Group>" scope="request"/>
<jsp:useBean id="modules" type="java.util.Map<fr.iut2.ProjetJPA.data.module.Module, java.lang.Float>" scope="request" />
<jsp:useBean id="globalAverage" type="java.lang.Float" scope="request" />
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - <%=student.getName()%></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header"><%=student.getName()%></div>
            <div class="card-body">
                <h2>Informations</h2>
                <form method="POST" action="<%=application.getContextPath()%>/do/updateStudent">

                    <!-- Hidden input to pass the studentId -->
                    <input type="hidden" name="studentId" value="<%=student.getId()%>">

                    <div class="mb-3 row">
                        <div class="col-auto">
                            <label for="firstnameInput" class="form-label">Prénom</label>
                            <input type="text" class="form-control" id="firstnameInput" name="firstname" value="<%=student.getFirstname()%>" required>
                        </div>
                        <div class="col-auto">
                            <label for="lastnameInput" class="form-label">Nom</label>
                            <input type="text" class="form-control" id="lastnameInput" name="lastname" value="<%=student.getLastname()%>" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="lastnameInput" class="form-label">Groupe</label>
                        <select class="form-select" aria-label="group select" name="groupId">
                            <% for (Group group : groups) { %>
                                <option value="<%= group.getId() %>" <%= student.getGroup().getId() == group.getId() ? "selected" : ""%>><%=group.getName()%></option>
                            <% } %>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">Sauvegarder</button>
                </form>

                <h2>Absences</h2>
                <% if (student.getAbsences().size() > 0) { %>
                    <ul class="list-group">
                        <table class="table table-hover">
                            <thead>
                                <th scope="col">Date de début</th>
                                <th scope="col">Date de fin</th>
                            </thead>
                            <tbody>
                                <% for (Absence abs : student.getAbsences()) { %>
                                    <tr>
                                        <td><%=abs.getStartTime()%></td>
                                        <td><%=abs.getEndTime()%></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </ul>
                <% } else { %>
                    <span>Aucunes absences.</span>
                <% } %>

                <h2>Notes</h2>
                <table class="table table-hover">
                    <thead>
                        <th scope="col">Module</th>
                        <th scope="col">Examen</th>
                        <th scope="col">Coef</th>
                        <th scope="col">Notes</th>
                        <th scope="col">Moyenne</th>
                    </thead>
                    <tbody>
                        <% for (Module module : modules.keySet()) { %>
                            <tr style="vertical-align: middle">
                                <td><%=module.getName()%></td>
                                <td>
                                    <% for (Exam exam : module.getExams()) { %>
                                        <p><%=exam.getName()%></p>
                                    <% } %>
                                </td>
                                <td>
                                    <% for (Exam exam : module.getExams()) { %>
                                    <p><%=exam.getCoeficient()%></p>
                                    <% } %>
                                </td>
                                <td>
                                    <% for (Exam exam : module.getExams()) {
                                        Mark studentMark = student.getMarkFromExam(exam);
                                    %>
                                        <% if (studentMark != null) { %>
                                            <p style="font-size: 1.1em">
                                                <span class="badge bg-<%=studentMark.getValue() <= 10 ? "danger" : "success"%>"><%=studentMark.getValue()%> / 20</span>
                                            </p>
                                        <% } else { %>
                                            <p style="font-size: 1.1em">
                                                <span class="badge bg-info">Non-renseignée</span>
                                            </p>
                                        <% } %>
                                    <% } %>
                                </td>
                                <td>
                                    <%
                                        float moduleAverage = modules.get(module);
                                        if (moduleAverage >= 0) {
                                    %>
                                        <p style="font-size: 1.2em" class="badge bg-<%=moduleAverage <= 10 ? "danger" : "success"%>"><%=moduleAverage%> / 20</p>
                                    <% } %>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3">Moyenne :</td>
                            <td style="font-size: 2em"><span class="badge bg-<%=globalAverage <= 10 ? "danger" : "success"%>"><%=globalAverage%> / 20</span></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>