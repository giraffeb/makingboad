<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>
<%@include file="header.jsp"%>


<h3> File Upload Pages</h3>

<div id="">
    <form name="uploadFilesForm" action="/uploadfiles" method="POST" enctype="multipart/form-data">
        <input type="file" name="files" multiple="multiple">
        <button type="submit">Send</button>
    </form>
</div>

<%@include file="footer.jsp"%>