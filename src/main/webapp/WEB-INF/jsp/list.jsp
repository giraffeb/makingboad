<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<html>

<body>
<%@ include file="login.jsp"%>
<h3> Post List </h3>

    <div id="list_area">
        <table id="post_list_table">
            <thead>
                <tr>
                    <th>no</th>
                    <th>title</th>
                    <th>writer</th>
                    <th>count</th>
                    <th>date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="post">
                <tr>
                    <td><a href="/post?post_id=${post.id_no}">${post.id_no}</a></td>
                    <td><a href="/post?post_id=${post.id_no}">${post.title}</a></td>
                    <td>${post.writer}</td>
                    <td>${post.count}</td>
                    <td>${post.writed_date}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>

</html>