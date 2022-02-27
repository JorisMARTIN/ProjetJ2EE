<%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 03/01/2022
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="mb-3">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%=application.getContextPath()%>">Gestion des étudiants</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link <% if(request.getRequestURI().startsWith(application.getContextPath() + "/WEB-INF/JSP/index.jsp")) {%>active<% } %>" aria-current="page" href="<%= application.getContextPath()%>/do/">Accueil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <% if(request.getRequestURI().startsWith(application.getContextPath() + "/WEB-INF/JSP/students")) {%>active<% } %>" aria-current="page" href="<%= application.getContextPath()%>/do/students">Étudiants</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <% if(request.getRequestURI().startsWith(application.getContextPath() + "/WEB-INF/JSP/absences")) {%>active<% } %>" aria-current="page" href="<%= application.getContextPath()%>/do/absences">Absences</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <% if(request.getRequestURI().startsWith(application.getContextPath() + "/WEB-INF/JSP/groups")) {%>active<% } %>" aria-current="page" href="<%= application.getContextPath()%>/do/groups">Groupes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <% if(request.getRequestURI().startsWith(application.getContextPath() + "/WEB-INF/JSP/modules")) {%>active<% } %>" aria-current="page" href="<%= application.getContextPath()%>/do/modules">Modules</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link <% if(request.getRequestURI().startsWith(application.getContextPath() + "/WEB-INF/JSP/exams")) {%>active<% } %>" aria-current="page" href="<%= application.getContextPath()%>/do/exams">Examens</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
