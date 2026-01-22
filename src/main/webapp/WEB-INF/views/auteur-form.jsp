<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:choose><c:when test="${auteur != null}"><fmt:message key="author.edit"/></c:when><c:otherwise><fmt:message key="author.add"/></c:otherwise></c:choose></title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
        }
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 30px;
        }
        .navbar h1 {
            font-size: 24px;
        }
        .container {
            max-width: 800px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: 500;
        }
        input[type="text"],
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
            transition: transform 0.2s;
        }
        .btn:hover {
            transform: translateY(-2px);
        }
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        .form-actions {
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="navbar">
    <h1><fmt:message key="app.title"/></h1>
</div>

<div class="container">
    <div class="form-container">
        <h2>
            <c:choose>
                <c:when test="${auteur != null}"><fmt:message key="author.edit"/></c:when>
                <c:otherwise><fmt:message key="author.add"/></c:otherwise>
            </c:choose>
        </h2>

        <form action="${pageContext.request.contextPath}/auteur" method="post">
            <input type="hidden" name="action" value="${auteur != null ? 'update' : 'insert'}">
            <c:if test="${auteur != null}">
                <input type="hidden" name="matricule" value="${auteur.matricule}">
            </c:if>

            <div class="form-group">
                <label for="nom"><fmt:message key="author.lastName"/>:</label>
                <input type="text" id="nom" name="nom" value="${auteur.nom}" required>
            </div>

            <div class="form-group">
                <label for="prenom"><fmt:message key="author.firstName"/>:</label>
                <input type="text" id="prenom" name="prenom" value="${auteur.prenom}" required>
            </div>

            <div class="form-group">
                <label for="genre"><fmt:message key="author.gender"/>:</label>
                <select id="genre" name="genre" required>
                    <option value="">-- <fmt:message key="author.selectGender"/> --</option>
                    <option value="Masculin" ${auteur.genre == 'Masculin' ? 'selected' : ''}><fmt:message key="author.male"/></option>
                    <option value="Féminin" ${auteur.genre == 'Féminin' ? 'selected' : ''}><fmt:message key="author.female"/></option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
                <a href="${pageContext.request.contextPath}/auteur?action=list" class="btn btn-secondary"><fmt:message key="cancel"/></a>
            </div>
        </form>
    </div>
</div>
</body>
</html>