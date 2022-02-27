<%@ page import="fr.iut2.ProjetJPA.data.group.Group" %>
<%@ page import="fr.iut2.ProjetJPA.data.student.Student" %>
<%@ page import="fr.iut2.ProjetJPA.data.module.Module" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 17/02/2022
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="groups" type="java.util.List<fr.iut2.ProjetJPA.data.group.Group>" scope="request"/>
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - Groupes</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card mb-3">
            <div class="card-header">
                <h2>Groupes</h2>
            </div>
            <div class="card-body">
                <h3>Liste des groupes</h3>
                <% for (Group group : groups) {%>
                    <div class="accordion" id="groupAccordion">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="heading-<%=group.getId()%>">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-<%=group.getId()%>" aria-expanded="false" aria-controls="collapse-<%=group.getId()%>">
                                    <%= group.getName() %>
                                </button>
                            </h2>
                            <div id="collapse-<%=group.getId()%>" class="accordion-collapse collapse" aria-labelledby="heading-<%=group.getId()%>">
                                <div class="accordion-body">
                                    <div class="mb-3 row justify-content-center">
                                        <div class="col-5">
                                            <h3 class="text-center">Etudiants du groupe</h3>
                                            <% if (group.getStudents().size() > 0) { %>
                                                <div class="list-group">
                                                    <% for (Student student : group.getStudents()) { %>
                                                        <a href="<%= application.getContextPath()%>/do/student?id=<%=student.getId()%>" class="list-group-item list-group-item-action"><%= student.getName() %></a>
                                                    <% } %>
                                                </div>
                                            <% } else { %>
                                                <p>Aucuns étudiants dans le groupe.</p>
                                            <% } %>
                                        </div>
                                        <div class="col-5">
                                            <h3 class="text-center">Modules suivits</h3>
                                            <% if (group.getModules().size() > 0) { %>
                                                <div class="list-group">
                                                    <% for (Module module : group.getModules()) { %>
                                                        <a href="<%= application.getContextPath()%>/do/module?id=<%=module.getId()%>" class="list-group-item list-group-item-action"><%= module.getName() %></a>
                                                    <% } %>
                                                </div>
                                            <% } else { %>
                                                <p>Aucuns modules suivits.</p>
                                            <% } %>
                                        </div>
                                    </div>
                                    <% if (group.getStudents().size() > 0) { %>
                                        <div class="alert alert-warning" role="alert">
                                            Vous ne pouvez pas supprimer le groupe tant que des étudiants appartiennent à ce dernier.
                                        </div>
                                        <a class="btn btn-danger disabled">Supprimer le groupe</a>
                                    <% } else { %>
                                        <a class="btn btn-danger" href="<%=application.getContextPath()%>/do/deleteGroup?id=<%=group.getId()%>">Supprimer le groupe</a>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Ajouter un groupe</h2>
            </div>
            <div class="card-body">
                <form method="post" action="<%=application.getContextPath()%>/do/newGroup">
                    <label for="group-name">Nom </label>
                    <input type="text" id="group-name" name="groupName" style="text-transform: uppercase" required/>

                    <button type="submit" class="btn btn-success">Ajouter</button>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
