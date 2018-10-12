<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<%@ include file="header.jsp"%>
<c:choose>
    <c:when test="${sessionScope.SPRING_SECURITY_CONTEXT eq null}">
    <div>
        <h3>login Form</h3>
        <form class="form-inline" action="/login" method="post" >
            <div class="form-group mx-sm-3 mb-2">
                <label >ID : </label>
                <input type="text" class="form-control" placeholder="Enter ID" name="username">
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <label >PW : </label>
                <input type="password" class="form-control" placeholder="Enter PW" name="password">
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">send</button>
        </form>
    </div>
    </c:when>

    <c:otherwise>
    <div>
        <h3>logOut Form</h3>
        <form class="form-inline" action="/logout" method="post" >
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">send</button>
        </form>
    </div>
    </c:otherwise>
</c:choose>


<%@include file="footer.jsp"%>