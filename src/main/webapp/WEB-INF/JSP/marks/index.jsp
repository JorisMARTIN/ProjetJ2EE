<%@ page import="fr.iut2.ProjetJPA.data.group.Group" %>
<%@ page import="fr.iut2.ProjetJPA.data.student.Student" %>
<%@ page import="fr.iut2.ProjetJPA.data.mark.Mark" %><%--
  Created by IntelliJ IDEA.
  User: joris
  Date: 27/02/2022
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="groups" type="java.util.List<fr.iut2.ProjetJPA.data.group.Group>" scope="request" />
<jsp:useBean id="marks" type="java.util.List<fr.iut2.ProjetJPA.data.mark.Mark>" scope="request" />
<jsp:useBean id="exam" type="fr.iut2.ProjetJPA.data.exam.Exam" scope="request" />
<jsp:useBean id="averages" type="java.util.Map<java.lang.Integer, java.lang.Float>" scope="request" />
<html>
    <head>
        <title><%= application.getInitParameter("title")%> - Notes</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3" onload="setup()">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header">
                <h2>Note de l'examen - <%=exam.getName()%></h2>
                <ul>
                    <li>Date : <%=exam.getDate()%></li>
                    <li>Coefficient : <%=exam.getCoeficient()%></li>
                </ul>
            </div>
            <div class="card-body">
                <% for (Group group : groups) { %>
                <div class="mb-2 p-2 border border-primary rounded">
                    <h3><span class="badge bg-primary"><%=group.getName()%></span></h3>
                    <form method="POST" action="<%= application.getContextPath()%>/do/saveMarks">
                        <input type="hidden" name="examId" value="<%=exam.getId()%>">
                        <input type="hidden" name="groupId" value="<%= group.getId() %>">

                        <% for (Student student : group.getStudents()) {
                            Mark studentMark = student.getMarkFromExam(exam);
                        %>
                        <div class="mb-2 row">
                            <div class="col-3">
                                <a href="<%=application.getContextPath()%>/do/student?id=<%=student.getId()%>"><%=student.getName()%></a>
                            </div>
                            <div class="col-3 row">
                                <div class="col">
                                    <input type="number" class="form-control" min="0" max="20" step="0.25" name="student-<%=student.getId()%>" value="<%= studentMark != null ? studentMark.getValue() : 0 %>">
                                </div>
                                <div class="col">
                                    <span>/ 20</span>
                                </div>
                            </div>
                        </div>
                        <% } %>
                        <div class="mb-2 float-end">
                            <p>Moyenne du groupe : <span class="badge bg-secondary"><%=averages.get(group.getId())%> / 20</span></p>

                        </div>

                        <button type="submit" class="btn btn-success">Sauvegarder</button>
                    </form>
                </div>
                <% }%>
            </div>
        </div>

    </body>
</html>
