<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<%@include file="header.jsp"%>


<h3> Post Page </h3>

<div id="update_area">
    <form action="updatePost" method="POST" class="form-row">
        <input type="hidden" name="post_id" value="${post.post_id}">
        <table class="table">
            <tbody>
                <tr class="row">
                    <td class="col-sm-2">title :</td>
                    <td class="col"><input name="title" class="col" type="text" value="${post.title}"/></td>
                </tr>
                <tr class="row">
                    <td class="col-sm-2">writer :</td>
                    <td class="col">${post.writer.username}</td>
                </tr>
                <tr class="row">
                    <td class="col-sm-2">view :</td>
                    <td class="col">${post.viewCount}</td>
                </tr>
                <tr class="row">
                    <td class="col-sm-2"></td>
                    <td class="col"><textarea name="content" style="resize:none; height: 250px;"  class="form-control" >${post.content}</textarea></td>
                </tr>
                <tr>
                    <td><button class="btn btn-primary" type="submit">send</button></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
    </form>
</div>

<%@include file="footer.jsp"%>