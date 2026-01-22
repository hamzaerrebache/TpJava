<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="book.list.title"/></title>
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
            align-items: center;
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
        .search-bar {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        .search-bar select,
        .search-bar input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
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
        .btn-primary {
            background: #667eea;
            color: white;
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
        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
        }
    </style>
</head>
<body>
<div class="navbar">
    <h1><fmt:message key="app.title"/></h1>
    <div class="navbar-right">
        <span><fmt:message key="welcome"/> ${sessionScope.user.login} (${sessionScope.user.role})</span>
        <a href="${pageContext.request.contextPath}/livre?action=list"><fmt:message key="menu.books"/></a>
        <c:if test="${sessionScope.user.role == 'Admin'}">
            <a href="${pageContext.request.contextPath}/auteur?action=list"><fmt:message key="menu.authors"/></a>
        </c:if>
        <a href="${pageContext.request.contextPath}/logout"><fmt:message key="menu.logout"/></a>
    </div>
</div>

<div class="container">
    <h2><fmt:message key="book.list.title"/></h2>

    <div class="search-bar">
        <form action="${pageContext.request.contextPath}/livre" method="get" style="display: flex; gap: 10px; flex: 1;">
            <input type="hidden" name="action" value="search">
            <select name="critere">
                <option value="titre" ${critere == 'titre' ? 'selected' : ''}><fmt:message key="book.title"/></option>
                <option value="auteur" ${critere == 'auteur' ? 'selected' : ''}><fmt:message key="book.author"/></option>
                <option value="date" ${critere == 'date' ? 'selected' : ''}><fmt:message key="book.date"/></option>
            </select>
            <input type="text" name="valeur" value="${valeur}" placeholder="<fmt:message key="book.search.placeholder"/>">
            <button type="submit" class="btn btn-primary"><fmt:message key="book.search"/></button>
            <a href="${pageContext.request.contextPath}/livre?action=list" class="btn btn-warning"><fmt:message key="book.viewAll"/></a>
        </form>

        <c:if test="${sessionScope.user.role == 'Admin'}">
            <a href="${pageContext.request.contextPath}/livre?action=new" class="btn btn-success"><fmt:message key="book.addNew"/></a>
        </c:if>
    </div>

    <table>
        <thead>
        <tr>
            <th>ISBN</th>
            <th><fmt:message key="book.title"/></th>
            <th><fmt:message key="book.description"/></th>
            <th><fmt:message key="book.date"/></th>
            <th><fmt:message key="book.publisher"/></th>
            <th><fmt:message key="book.author"/></th>
            <c:if test="${sessionScope.user.role == 'Admin'}">
                <th><fmt:message key="actions"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty livres}">
                <tr>
                    <td colspan="7" class="no-data"><fmt:message key="book.noData"/></td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="livre" items="${livres}">
                    <tr>
                        <td>${livre.isbn}</td>
                        <td>${livre.titre}</td>
                        <td>${livre.description}</td>
                        <td><fmt:formatDate value="${livre.dateEdition}" pattern="yyyy-MM-dd"/></td>
                        <td>${livre.editeur}</td>
                        <td>${livre.auteur != null ? livre.auteur : 'N/A'}</td>
                        <c:if test="${sessionScope.user.role == 'Admin'}">
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/livre?action=edit&isbn=${livre.isbn}"
                                   class="btn btn-warning"><fmt:message key="edit"/></a>
                                <a href="${pageContext.request.contextPath}/livre?action=delete&isbn=${livre.isbn}"
                                   class="btn btn-danger"
                                   onclick="return confirm('<fmt:message key="book.confirmDelete"/>')"><fmt:message key="delete"/></a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>