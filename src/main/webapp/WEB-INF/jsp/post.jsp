<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<html>

<body>
    <h3> Post Page</h3>

    <div id="post_area">
        <div id="title_area">
            <strong>title : </strong><span>${post.title}</span><br>
            <Strong>writer : </Strong><span>${post.writer}</span><br>
            <strong>date : </strong><span>${post.writed_date}</span><br>
            <strong>count : </strong><span>${post.count}</span><br>
        </div>
        <div id="content_area">
            ${post.content}
        </div>
    </div>

</body>

</html>