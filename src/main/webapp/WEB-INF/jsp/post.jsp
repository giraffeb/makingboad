<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="utf-8" %>

<%@include file="header.jsp"%>
<style>
    table{
        min-height: 450px;
    }
    td{
        white-space: pre;
    }
</style>
<div>
    <table class="table">
        <thead>
            <tr class="row">
                <td class="col-2 text-center"><span><strong>title: </strong></span></td>
                <td class="col"><span><strong>${post.title}</strong></span></td>
            </tr>
            <tr class="row">
                <td class="col-2 text-center"><span><strong>date: </strong></span></td>
                <td class="col">${fn:substring(post.registeredDate,0,10)}</td>
            </tr>
            <tr class="row">
                <td class="col-2 text-center"><span><strong>writer: </strong></span></td>
                <td class="col">${post.writer.username}</td>
            </tr>
            <tr class="row">
                <td class="col-2 text-center"><span><strong>view: </strong></span></td>
                <td class="col">${post.viewCount}</td>
            </tr>
        </thead>
        <tbody>
            <tr class="row">
                <td class="col-2"></td>
                <td class="col">${post.content}</td>
            </tr>
        </tbody>
    </table>
    <div id="button_area" class="row">
        <c:if test="${post.writer.username eq sessionScope.username}">
            <div class="col-xs-1">
                <a class="btn btn-primary btn-sm" href="updatePost?post_id=${post.post_id}">수정하기</a>
            </div>
            <div class="col-xs-1">
                <form method="post" action="deletePost?post_id=${post.post_id}">
                    <button class="btn btn-primary btn-sm" type="submit" value="삭제하기">삭제하기</button>
                </form>
            </div>
        </c:if>
        <div class="col">
            <a class="btn btn-primary btn-sm float-right" href="list">목록</a>
        </div>

    </div>
    <div id="comments_area">
        <div>
            <form action="postComments" method="post">
            <input type="hidden" name="post_id" value="${post.post_id}">
            <label for="comments_content">댓글작성하기</label>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3">${sessionScope.username}</span>
                </div>
                <input type="text" class="form-control" id="comments_content" name="comments_content" aria-describedby="basic-addon3">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="submit">작성하기</button>
                </div>
            </div>
            </form>
        </div>
        <div>
            <c:forEach var="comments" items="${comments_list}">
            <div class="row">
                <div class="col-2">${comments.writer.username}</div>
                <div class="col-8">${comments.content}</div>

                <c:if test="${sessionScope.username eq comments.writer.username}">
                    <form action="deleteComments?comment_id=${comments.comments_id}" method="post">
                        <input type="hidden" name="post_id" value="${post.post_id}">
                    <div class="col-2">
                        <button class="btn btn-outline-secondary" type="submit">삭제</button>
                    </div>
                    </form>
                </c:if>
            </div>
            </c:forEach>
        </div>

    </div>

</div>

<%@include file="footer.jsp"%>