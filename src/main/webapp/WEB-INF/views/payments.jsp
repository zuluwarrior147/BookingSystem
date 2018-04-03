<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value= "${sessionScope.locale}" />
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
    <style>
        .custab {
            border: 1px solid #ccc;
            padding: 5px;
            margin: 5% 0;
            box-shadow: 3px 3px 2px #ccc;
            transition: 0.5s;
        }

        .custab:hover {
            box-shadow: 3px 3px 0px transparent;
            transition: 0.5s;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>

<div class="container">
    <div class="row col-md-10 col-md-offset-1 custyle">
        <div class="panel-title text-center">
            <c:if test="${not sessionScope.user.isAdmin()}">
                <h1 class="title"><fmt:message key="your.payments"/></h1>
            </c:if>
            <c:if test="${sessionScope.user.isAdmin()}">
                <h1 class="title"><fmt:message key="payments"/></h1>
            </c:if>
            <hr/>
        </div>
        <c:choose>
            <c:when test="${not empty requestScope.payments}">
                <table class="table table-striped custab">
                    <thead>
                    <tr>
                        <th><fmt:message key="payment.account.from"/></th>
                        <th><fmt:message key="payment.account.to"/></th>
                        <th><fmt:message key="payment.amount"/></th>
                        <th><fmt:message key="date"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="payment" items="${requestScope.payments}">
                        <tr>
                            <td><c:out value="${payment.getAccountFrom().getAccountNumber()}"/></td>
                            <td><c:out value="${payment.getAccountTo().getAccountNumber()}"/></td>
                            <td>
                                <c:out value="${payment.getAmount()}"/>
                                <fmt:message key="currency"/>
                            </td>
                            <td><fmt:formatDate type = "both" value="${payment.getDate()}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">
                    <strong><fmt:message key="info"/></strong> <fmt:message key="payment.no.matches"/>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
