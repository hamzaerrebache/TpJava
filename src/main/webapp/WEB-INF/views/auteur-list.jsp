<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="author.list.title"/></title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar h1 {
            font-size: 24px;
        }
        .navbar-right {
            display: flex;
            gap: 15px;
        }
        .navbar a {
            color: white;
            text-decoration: none;
            padding: 8px 15px;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .navbar a:hover {
            background: rgba(255,255,255,0.2);
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .header-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            transition: transform 0.2s;
        }
        .btn:hover {
            transform: translateY(-2px);
        }
        .btn-success {
            background: #28a745;
            color: white;
        }
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        .btn-warning {
            background: #ffc107;
            color: #333;
        }
        table {
            width: 100%;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        thead {
            background: #667eea;
            color: white;
        }
        th, td {
            padding: 15px;
            text-align: left;
        }
        tbody tr:hover {
            background: #f8f9fa;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
    </style>
</head>
<body>
<div class="navbar">
    <h1><fmt:message key="app.title"/></h1>
    <div class="navbar-right">
        <a href="${pageContext.request.contextPath}/livre?action=list"><fmt:message key="menu.books"/></a>
        <a href="${pageContext.request.contextPath}/auteur?action=list"><fmt:message key="menu.authors"/></a>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="menu.logout"/></a>
    </div>
</div>

<div class="container">
    <div class="header-actions">
        <h2><fmt:message key="author.list.title"/></h2>
        <a href="${pageContext.request.contextPath}/auteur?action=new" class="btn btn-success"><fmt:message key="author.addNew"/></a>
    </div>

    <table>
        <thead>
        <tr>
            <th><fmt:message key="author.id"/></th>
            <th><fmt:message key="author.lastName"/></th>
            <th><fmt:message key="author.firstName"/></th>
            <th><fmt:message key="author.gender"/></th>
            <th><fmt:message key="actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="auteur" items="${auteurs}">
            <tr>
                <td>${auteur.matricule}</td>
                <td>${auteur.nom}</td>
                <td>${auteur.prenom}</td>
                <td>${auteur.genre}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/auteur?action=edit&matricule=${auteur.matricule}"
                       class="btn btn-warning"><fmt:message key="edit"/></a>
                    <a href="${pageContext.request.contextPath}/auteur?action=delete&matricule=${auteur.matricule}"
                       class="btn btn-danger"
                       onclick="return confirm('<fmt:message key="author.confirmDelete"/>')"><fmt:message key="delete"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>