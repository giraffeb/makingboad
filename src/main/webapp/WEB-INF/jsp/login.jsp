<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<%@ include file="header.jsp"%>
<div>
    <h3>login Form</h3>
    <form action="/login" method="post" >
        <div class="form-group mx-sm-3 mb-2">
            <label >ID : </label>
            <input type="text" class="form-control" placeholder="Enter ID" name="username">
        </div>
        <div class="form-group mx-sm-3 mb-2">
            <label >PW : </label>
            <input type="password" class="form-control" placeholder="Enter PW" name="password">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">보내기</button>
            <a class="btn btn-primary text-white" href="/list">취소</a>
            <a class="btn btn-danger text-white" href="/signup">회원가입</a>
        </div>
    </form>
</div>


<%@include file="footer.jsp"%>