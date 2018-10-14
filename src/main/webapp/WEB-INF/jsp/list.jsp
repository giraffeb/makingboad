<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<%@ include file="header.jsp"%>

<h3> Post List </h3>

    <div id="list_area">
        <table id="post_list_table" class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">no</th>
                    <th scope="col">title</th>
                    <th scope="col">writer</th>
                    <th scope="col">count</th>
                    <th scope="col">date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="post">
                <tr>
                    <th scope="row"><a href="/post?post_id=${post.id_no}">${post.id_no}</a></th>
                    <td><a href="/post?post_id=${post.id_no}">${post.title}</a></td>
                    <td>${post.writer}</td>
                    <td>${post.count}</td>
                    <td>${post.writed_date}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <a class="btn btn-primary" href="/writePost">write</a>
        </div>
    </div>

<%@include file="footer.jsp"%>