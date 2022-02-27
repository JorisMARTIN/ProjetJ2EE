<%@ page import="fr.iut2.ProjetJPA.data.module.Module" %>
<%@ page import="fr.iut2.ProjetJPA.data.group.Group" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 27/02/2022
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="modules" type="java.util.List<fr.iut2.ProjetJPA.data.module.Module>" scope="request" />
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - Modules</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header">
                <h2>Modules</h2>
            </div>
            <div class="card-body">
                <% if (modules.size() > 0) { %>
                    <div class="container">
                        <div class="row">
                            <% for (Module module : modules) { %>
                                <div class="col-4">
                                    <div class="card" >
                                        <div class="card-header" style="height: 100px">
                                            <h3><%=module.getName()%></h3>
                                        </div>
                                        <div class="card-body" style="height: 200px; overflow: auto">
                                            <p class="card-text">Groupe suivant le module</p>
                                            <ul class="list-group">
                                                <% for (Group group : module.getGroups()) { %>
                                                    <li class="list-group-item"><%=group.getName()%></li>
                                                <% } %>
                                            </ul>
                                        </div>
                                        <div class="card-footer">
                                            <a href="<%=application.getContextPath()%>/do/module?id=<%=module.getId()%>" class="btn btn-primary">Détail</a>
                                        </div>
                                    </div>
                                </div>
                            <% } %>
                        </div>
                    </div>
                <% } else { %>
                    <span>Aucun module disponibles.</span>
                <% } %>
            </div>
            <div class="card-footer">
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModuleModal">
                    Ajouter un module
                </button>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="addModuleModal" tabindex="-1" aria-labelledby="addModuleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ajouter un module</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="<%=application.getContextPath()%>/do/newModule">
                            <div class="mb-3">
                                <label for="nameInput" class="form-label">Nom</label>
                                <input type="text" class="form-control" id="nameInput" name="name" required>
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
