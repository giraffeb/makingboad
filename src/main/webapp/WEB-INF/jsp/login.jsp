<!DOCTYPE html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<c:if test="${sessionScope.loginStatus == null}">
    <div >
        <h3>login Form</h3>
        <form class="form-inline" action="/loginCheck" method="POST" >
            <div class="form-group mx-sm-3 mb-2">
                <label >ID : </label>
                <input type="text" class="form-control" placeholder="Enter ID" name="id">
            </div>
            <div class="form-group mx-sm-3 mb-2">
                <label >PW : </label>
                <input type="password" class="form-control" placeholder="Enter PW" name="pw">
            </div>
            <button type="submit" class="btn btn-primary">send</button>
        </form>
    </div>
</c:if>

<c:if test="${sessionScope.loginStatus != null}">
    <h4> Welcome ${sessionScope.user_id}!!</h4>
</c:if>