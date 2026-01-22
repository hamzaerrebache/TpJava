<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:choose><c:when test="${livre != null}"><fmt:message key="book.edit"/></c:when><c:otherwise><fmt:message key="book.add"/></c:otherwise></c:choose></title>
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
        input[type="number"],
        input[type="date"],
        select,
        textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
        }
        textarea {
            min-height: 100px;
            resize: vertical;
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
                <c:when test="${livre != null}"><fmt:message key="book.edit"/></c:when>
                <c:otherwise><fmt:message key="book.add"/></c:otherwise>
            </c:choose>
        </h2>

        <form action="${pageContext.request.contextPath}/livre" method="post">
            <input type="hidden" name="action" value="${livre != null ? 'update' : 'insert'}">

            <div class="form-group">
                <label for="isbn">ISBN:</label>
                <input type="number" id="isbn" name="isbn" value="${livre.isbn}"
                ${livre != null ? 'readonly' : ''} required>
            </div>

            <div class="form-group">
                <label for="titre"><fmt:message key="book.title"/>:</label>
                <input type="text" id="titre" name="titre" value="${livre.titre}" required>
            </div>

            <div class="form-group">
                <label for="description"><fmt:message key="book.description"/>:</label>
                <textarea id="description" name="description">${livre.description}</textarea>
            </div>

            <div class="form-group">
                <label for="dateEdition"><fmt:message key="book.date"/>:</label>
                <input type="date" id="dateEdition" name="dateEdition"
                       value="<fmt:formatDate value='${livre.dateEdition}' pattern='yyyy-MM-dd'/>" required>
            </div>

            <div class="form-group">
                <label for="editeur"><fmt:message key="book.publisher"/>:</label>
                <select id="editeur" name="editeur" required>
                    <option value="">-- <fmt:message key="book.selectPublisher"/> --</option>
                    <option value="ENI" ${livre.editeur == 'ENI' ? 'selected' : ''}>ENI</option>
                    <option value="DUNOD" ${livre.editeur == 'DUNOD' ? 'selected' : ''}>DUNOD</option>
                    <option value="FIRST" ${livre.editeur == 'FIRST' ? 'selected' : ''}>FIRST</option>
                </select>
            </div>

            <div class="form-group">
                <label for="matricule"><fmt:message key="book.author"/>:</label>
                <select id="matricule" name="matricule" required>
                    <option value="">-- <fmt:message key="book.selectAuthor"/> --</option>
                    <c:forEach var="auteur" items="${auteurs}">
                        <option value="${auteur.matricule}"
                            ${livre.matricule == auteur.matricule ? 'selected' : ''}>
                                ${auteur.prenom} ${auteur.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
                <a href="${pageContext.request.contextPath}/livre?action=list" class="btn btn-secondary"><fmt:message key="cancel"/></a>
            </div>
        </form>
    </div>
</div>
</body>
</html>