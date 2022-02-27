<%@ page import="fr.iut2.ProjetJPA.data.module.Module" %>
<%@ page import="fr.iut2.ProjetJPA.data.exam.Exam" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 27/02/2022
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="modules" type="java.util.List<fr.iut2.ProjetJPA.data.module.Module>" scope="request" />
<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%> - Examens</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header">
                <h2>Examens</h2>
            </div>
            <div class="card-body">
                <div class="container-fluid">
                    <% for (int i = 0; i < modules.size(); i++) {
                        Module module = modules.get(i);
                    %>
                        <div class="row mb-2 align-items-center alert alert-<%= i % 2 == 0 ? "dark" : "secondary" %>">
                            <div class="col-2">
                                <%=module.getName()%>
                            </div>
                            <div class="col-10">
                                <% if (module.getExams().size() > 0) { %>
                                    <% for (Exam exam : module.getExams()) { %>
                                        <div class="row mb-2">
                                            <div class="col-4">
                                                <%= exam.getName() %>
                                            </div>
                                            <div class="col-4">
                                                <%= exam.getDate()%>
                                            </div>
                                            <div class="col-2">
                                                Coef. <%=exam.getCoeficient()%>
                                            </div>
                                            <div class="col-2">
                                                <a class="btn btn-primary" href="<%=application.getContextPath()%>/do/marks?examId=<%=exam.getId()%>">Notes</a>
                                            </div>
                                        </div>
                                    <% } %>
                                <% } else { %>
                                    <b>Pas d'examens pour ce module</b>
                                <% } %>
                            </div>
                        </div>
                    <% } %>
                </div>
            </div>
            <div class="card-footer">
                <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#addExamModal">Ajouter un examen</button>
            </div>
        </div>

        <!-- Modal add exam -->
        <div class="modal fade" id="addExamModal" tabindex="-1" aria-labelledby="addExamModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ajouter un examen</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form method="POST" action="<%=application.getContextPath()%>/do/newExam">
                            <div class="mb-3">
                                <label for="moduleSelect" class="form-label">Module</label>
                                <select class="form-select" aria-label="Module select" id="moduleSelect" name="moduleId" required>
                                    <% for (Module module : modules) { %>
                                        <option value="<%=module.getId()%>"><%=module.getName()%></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="nameInput" class="form-label">Nom</label>
                                <input type="text" class="form-control" id="nameInput" name="name" required>
                            </div>
                            <div class="mb-3 row">
                                <div class="col-6">
                                    <label for="dateInput" class="form-label">Date</label>
                                    <input type="datetime-local" class="form-control" id="dateInput" name="date" required>
                                </div>
                                <div class="col-6">
                                    <label for="coefInput" class="form-label">Coeficient</label>
                                    <input type="number" class="form-control" id="coefInput" name="coef" min="1" step="1" value="1" required>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-success float-end">Valider</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
