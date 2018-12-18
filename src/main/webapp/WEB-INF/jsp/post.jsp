<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="utf-8" %>

<%@include file="header.jsp"%>
<style>
    table{
        height: 450px;
    }
</style>
<div>
    <table class="table table-striped">
        <thead class="thead-light">
            <tr class="row">
                <th class="col-sm-2">title: </th>
                <td class="col"><strong>${post.title}</strong></td>
            </tr>
            <tr class="row">
                <th class="col-sm-2">date: </th>
                <td class="col">${fn:substring(post.registeredDate,0,10)}</td>
            </tr>
            <tr class="row">
                <th class="col-sm-2">writer: </th>
                <td class="col">${post.writer.username}</td>
            </tr>
            <tr class="row">
                <th class="col-sm-2">view: </th>
                <td class="col">${post.viewCount}</td>
            </tr>
        </thead>
        <tbody>
            <tr class="row">
                <th></th>
                <td>${post.content}</td>
            </tr>
        </tbody>
    </table>
    <div id="button_area" class="row">
        <c:if test="${post.writer.username eq sessionScope.username}">
            <div class="col-lg-1">
                <a class="btn btn-primary btn-sm" href="/updatePost?post_id=${post.post_id}">수정하기</a>
            </div>
            <div class="col-lg-1">
                <form method="post" action="/deletePost?post_id=${post.post_id}">
                    <button class="btn btn-primary btn-sm" type="submit" value="삭제하기">삭제하기</button>
                </form>
            </div>
        </c:if>
        <div class="col-lg-1 offset-lg-9">
            <a class="btn btn-primary btn-sm" href="/list">목록</a>
        </div>

    </div>

</div>

<%@include file="footer.jsp"%>