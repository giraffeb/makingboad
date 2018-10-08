<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<%@include file="header.jsp"%>

<h3> Post Page </h3>

<div id="">
    <form name="postForm" action="/writePost" method="POST">
        <div class="form-group" id="title_aera">
            <input type="text" class="form-control" name="title" placeholder="Enter Title"></input>
        </div>
        <div class="form-group" id="content_area">
            <textarea class="form-control" name="content" placeholder="Enter Content"></textarea>
        </div>
        <button class="btn btn-primary" type="submit">Send</button>
    </form>
</div>

<%@include file="footer.jsp"%>