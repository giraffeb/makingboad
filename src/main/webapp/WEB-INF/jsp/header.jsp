<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<style>
    html{
        width : 800px;
        margin: auto;
    }

</style>

<html>

<head>
    <title>Hello JSP board</title>
    <!-- 합쳐지고 최소화된 최신 CSS -->
    <link rel="stylesheet" href="/bootstrap-3.3.2-dist/css/bootstrap.css">

    <!-- 부가적인 테마 -->
    <link rel="stylesheet" href="/bootstrap-3.3.2-dist/css/bootstrap-theme.min.css">

    <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
    <script src="hbootstrap-3.3.2-dist/js/bootstrap.js"></script>

    <%@include file="login.jsp"%>
</head>
<body>