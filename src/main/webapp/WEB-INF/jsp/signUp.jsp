<!DOCTYPE html>


<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>


<%@ include file="header.jsp"%>

    <div id="title_area" style="margin-bottom: 40px;">
        <h3>회원가입 페이지</h3>
    </div>
    <div id="sign_up_area">
        <form action="signup" method="post" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="form-group row">
                <label for="username" class="col-form-label col-sm-3">아이디</label>
                <input id="username" name="username" type="text" class="form-control col-sm-6" >
                <label for="username" class="col-form-label col-sm-6 offset-sm-4">${username}</label>
            </div>
            <div class="form-group row">
                <label for="password" class="col-form-label col-sm-3">비밀번호</label>
                <input id="password" name="password" type="password" class="form-control col-sm-6" placeholder=""></input>
                <small id="emailHelp" class="col-sm-6 form-text text-muted">특수문자, 숫자포함 8~15자로 설정해주세요.</small>
                <label for="password" class="col-form-label col-sm-6  offset-sm-4">${password}</label>
            </div>

            <%--<div class="form-group row">--%>
                <%--<label for="password" class="col-form-label col-sm-3">비밀번호 확인</label>--%>
                <%--<input id="password_check" name="password_check" type="password" class="form-control col-sm-6">--%>
            <%--</div>--%>

            <button class="btn btn-primary" type="submit">전송</button>

        </form>
    </div>
<%@include file="footer.jsp"%>