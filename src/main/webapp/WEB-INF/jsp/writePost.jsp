<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<%@include file="header.jsp"%>

<h3> Post Page </h3>

<div id="">
    <form method="post" action="writePost">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="form-row">
            <div class="form-group col">
                <label for="title">title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요.">
            </div>
        </div>
        <div class="form-group">
            <label for="content">content</label>
            <textarea style="height:250px; resize:none; " type="text" class="form-control" id="content" name="content" placeholder="input_title"></textarea>
        </div>
        <div>

        </div>
        <button type="submit" class="btn btn-primary">작성완료</button>
    </form>
</div>

<%@include file="footer.jsp"%>