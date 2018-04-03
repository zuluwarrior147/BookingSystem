<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>

<c:if test="${not empty requestScope.messages}">
    <div class="alert alert-success">
        <c:forEach items="${requestScope.messages}" var="message">
            <strong><fmt:message key="info"/></strong> <fmt:message key="${message}"/><br>
        </c:forEach>
    </div>
</c:if>

<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
        </c:forEach>
    </div>
</c:if>

<div class="container">
    <div class="row col-md-10 col-md-offset-1 custyle">
        <div class="panel-title text-center">
            <h1 class="title"><fmt:message key="account.replenish"/></h1>
            <hr/>
        </div>
            <form class="form-inline text-center" method="post">
                <div class="form-group">
                    <label for="account">
                        <fmt:message key="select.account"/>
                    </label>
                    <select name="account" class="form-control" id="account">
                        <c:forEach var="account" items="${requestScope.accounts}">
                            <c:if test="${account.isActive()}">
                                <option>${account.getAccountNumber()}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="amount">
                        <fmt:message key="amount"/>
                    </label>
                    <input name="amount" type="text" class="form-control" id="amount"
                           placeholder="<fmt:message key="enter.amount" />">
                </div>
                <button type="submit" class="btn btn-default">
                    <fmt:message key="replenish"/>
                </button>
            </form>
    </div>
</div>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
