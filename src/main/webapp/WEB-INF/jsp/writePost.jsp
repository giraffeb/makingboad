<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<html>

<body>

<h3> Post Page </h3>

<div id="">
    <form name="postForm" action="/writePost" method="POST">
        <div id="title_aera">
            <textarea name="title">

            </textarea>
        </div>
        <div id="content_area">
            <textarea name="content">

            </textarea>
        </div>
        <button type="submit">Send</button>
    </form>
</div>

</body>

</html>