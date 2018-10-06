<!DOCTYPE html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<c:if test="${sessionScope.loginStatus == null}">
    <div>
        <h3>login Form</h3>
        <form action="/loginCheck" method="POST">
            <span>ID : </span>
            <input type="text" name="id">
            <span>PW : </span>
            <input type="password" name="pw">
            <button type="submit">send</button>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.loginStatus != null}">
    <h3> session : ${sessionScope.loginStatus} </h3>
</c:if>