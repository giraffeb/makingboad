<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<html>

<body>

Post List :
<c:forEach items="${list}" var="row">
    <c:out value="${row}"/> <br>
</c:forEach>

</body>

</html>