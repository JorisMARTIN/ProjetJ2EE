<%@ page import="fr.iut2.ProjetJPA.data.group.Group" %>
<%@ page import="fr.iut2.ProjetJPA.data.exam.Exam" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 27/02/2022
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="module" type="fr.iut2.ProjetJPA.data.module.Module" scope="request" />
<jsp:useBean id="groups" type="java.util.List<fr.iut2.ProjetJPA.data.group.Group>" scope="request" />
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - Module</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header">
                <h2>Module - <%=module.getName()%></h2>
            </div>
            <div class="card-body">

                <form method="POST" action="<%=application.getContextPath()%>/do/updateModule">

                    <input type="hidden" name="moduleId" value="<%=module.getId()%>">

                    <div class="mb-3">
                        <label for="nameInput" class="form-label">Nom</label>
                        <input type="text" class="form-control" id="nameInput" name="name" value="<%=module.getName()%>" required>
                    </div>
                    <div class="mb-3">
                        <label>Groupes suivants le module</label>
                        <% for (Group group : groups) { %>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="" name="groupId-<%=group.getId()%>" id="checkBox-<%=group.getId()%>" <%= module.getGroups().contains(group) ? "checked" : ""%>>
                                <label class="form-check-label" for="checkBox-<%=group.getId()%>">
                                    <%= group.getName()%>
                                </label>
                            </div>
                        <% } %>
                    </div>

                    <button type="submit" class="btn btn-primary">Sauvegarder</button>
                </form>

                <h3>Examens du module</h3>
                <% if (module.getExams().size() > 0) { %>

                    <table class="table table-hover">
                        <thead>
                            <th scope="col">Nom</th>
                            <th scope="col">Date</th>
                            <th scope="col">Coeficient</th>
                            <th scope="col"></th>
                        </thead>
                        <tbody>
                            <% for (Exam exam : module.getExams()) { %>
                                <tr>
                                    <td><%=exam.getName()%></td>
                                    <td><%=exam.getDate()%></td>
                                    <td><%=exam.getCoeficient()%></td>
                                    <td><a href="<%=application.getContextPath()%>/do/exam?id=<%=exam.getId()%>" class="btn btn-primary">Détail</a></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p>Pas d'examen disponible</p>
                <% } %>
                <a href="<%= application.getContextPath()%>/do/exams" class="btn btn-primary">Ajouter des examens</a>
            </div>
            <div class="card-footer">
                <a href="<%=application.getContextPath()%>/do/deleteModule?id=<%=module.getId()%>" class="btn btn-danger float-end">Supprimer le module</a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
