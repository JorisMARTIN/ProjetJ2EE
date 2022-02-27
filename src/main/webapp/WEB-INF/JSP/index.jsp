<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="fr">
    <head>
        <title><%= application.getInitParameter("title")%></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body class="p-3">
        <jsp:include page='<%= application.getInitParameter("header")%>'/>

        <div class="card">
            <div class="card-header">Gestion des étudiants</div>
            <div class="card-body">
                <h5 class="card-title">Bienvenue sur l'application de gestion des étudiants.</h5>
                <p class="card-text">Sur cette plateforme vous pourez gerer l'enssemble des informations liées aux étudiants.</p>
                <ul>
                    <li><a href="<%= application.getContextPath() %>/do/students">Étudiants</a></li>
                    <li><a href="<%= application.getContextPath() %>/do/groups">Groupes</a></li>
                    <li><a href="<%= application.getContextPath() %>/do/absences">Absences</a></li>
                    <li><a href="<%= application.getContextPath() %>/do/marks">Notes</a></li>
                    <li>...</li>
                </ul>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>