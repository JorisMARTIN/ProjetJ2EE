<%@ page import="fr.iut2.ProjetJPA.data.student.Student" %>
<%@ page import="fr.iut2.ProjetJPA.data.group.Group" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 16/02/2022
  Time: 22:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="students" type="java.util.List<fr.iut2.ProjetJPA.data.student.Student>" scope="request"/>
<jsp:useBean id="groups" type="java.util.List<fr.iut2.ProjetJPA.data.group.Group>" scope="request" />
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - Étudiants</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header">
                <h2>Étudiants</h2>
            </div>
            <div class="card-body">
                <h3>Liste des étudiants</h3>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">NOM</th>
                            <th scope="col">Prénom</th>
                            <th scope="col">Groupe</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Student student : students) {%>
                            <tr>
                                <td><%=student.getId()%></td>
                                <td><%=student.getLastname().toUpperCase()%></td>
                                <td><%=student.getFirstname()%></td>
                                <td><%=student.getGroup().getName()%></td>
                                <td><a href="<%= application.getContextPath()%>/do/student?id=<%=student.getId()%>" class="btn btn-primary">Detail</a></td>
                            </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <div class="card-footer">
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addStudentModal">
                    Ajouter un étudiant
                </button>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="addStudentModal" tabindex="-1" aria-labelledby="addStudentModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ajouter un étudiant</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="<%=application.getContextPath()%>/do/newStudent">
                            <div class="mb-3">
                                <label for="lastname" class="form-label">Nom</label>
                                <input type="text" class="form-control" id="lastname" name="lastname" required>
                            </div>
                            <div class="mb-3">
                                <label for="firstname" class="form-label">Prénom</label>
                                <input type="text" class="form-control" id="firstname" name="firstname" required>
                            </div>
                            <div class="mb-3">
                                <label for="groupSelect" class="form-label">Groupe</label>
                                <select class="form-select" aria-label="group select" id="groupSelect" name="groupId">
                                    <% for (Group group : groups) { %>
                                        <option value="<%= group.getId() %>"><%=group.getName()%></option>
                                    <% } %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Sauvegarder</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
