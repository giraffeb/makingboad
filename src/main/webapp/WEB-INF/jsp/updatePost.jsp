<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<html>

<body>

<h3> Post Page </h3>

<div id="update_area">
    <form action="/updatePost" method="POST">
    <div id="title_area">
        <input type="hidden" name="post_id" value="${post.id_no}">
        <input name="title" type="text" value="${post.title}"/>
    </div>
    <div id="content_area">
        <input name="content" type="text" value="${post.content}"/>
        <button type="submit">send</button>
    </div>
    </form>
</div>

</body>

</html>