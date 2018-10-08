<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<%@include file="header.jsp"%>
    <style>
        #content_area{
            min-height: 200px;
        }
    </style>

    <h4> Post Page</h4>

    <div class="container" id="post_area">
        <div  id="title_area">
            <div class="row">
                <strong>title :</strong>
                <span>${post.title}</span>
            </div>
            <div class="row">
                <strong>writer :</strong>
                <span>${post.writer}</span>
            </div>
            <div class="row">
                <strong>date :</strong>
                <span>${post.writed_date}</span>
            </div>
            <div class="row">
                <strong>count :</strong>
                <span>${post.count}</span>
            </div>
        </div>
        <div class="row" id="content_area">
            ${post.content}
        </div>
        <div class="row">
            <a class="btn btn-primary" href="/updatePost?post_id=${post.id_no}">modify</a>
        </div>
    </div>

<%@include file="footer.jsp"%>