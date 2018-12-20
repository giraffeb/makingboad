<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page pageEncoding="utf-8" %>

<%@ include file="header.jsp"%>

<h3> 리스트페이지 </h3>
    <div id="list_area" style="background-color: white;" class="container">
        <table id="post_list_table" class="table table-hover table-striped ">
            <thead>
                <tr class="row">
                    <th scope="col" class="col-1">no</th>
                    <th scope="col" class="col-6">title</th>
                    <th scope="col" class="col-2">writer</th>
                    <th scope="col" class="col-1 d-none d-sm-table-cell">count</th>
                    <th scope="col" class="col-2 d-none d-sm-table-cell">date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="post">
                <tr class="row" href="post?post_id=${post.post_id}">
                    <th scope="row" class="col-1"><a href="post?post_id=${post.post_id}">${post.post_id}</a></th>
                    <td class="col-6"><a href="post?post_id=${post.post_id}">${post.title}</a></td>
                    <td class="col-2">${post.writer.username}</td>
                    <td class="col-1 d-none d-sm-table-cell">${post.viewCount}</td>
                    <td class="col-2 d-none d-sm-table-cell">${fn:substring(post.registeredDate,0,10)}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <div>
            <a class="btn btn-primary" href="write">write</a>
        </div>
        <div>
            <nav>
                <ul class="pagination justify-content-center">
                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                    <c:forEach var="cur" begin="1" end="${maxPageNumber}">
                        <li class="page-item"><a class="page-link" href="list?currentPage=${cur-1}">${cur}</a></li>
                    </c:forEach>
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </ul>
            </nav>
        </div>
    </div>
<%@include file="footer.jsp"%>