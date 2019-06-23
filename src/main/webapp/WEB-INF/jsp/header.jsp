<%@ page import="java.io.File" %>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page pageEncoding="utf-8" %>

<c:set var="appPath" value="${pageContext.request.contextPath}" ></c:set>
<html>
<%--<%@ include file="login.jsp"%>--%>
<head>
    <title>Hello JSP board - "${appPath}"</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous" async deferr></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous" async defer></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous" async defer></script>

    <%--    google oauth api--%>
    <script src="https://apis.google.com/js/platform.js" defer></script>
    <%-- google api javascript code--%>
    <script src="${pageContext.request.contextPath}/js/google_oauth_main.js" charset="utf-8" defer onload="googleInit()"></script>

    <%--facebook oauth api--%>
    <script async defer crossorigin="anonymous" src="https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v3.3&appId=2208086505911469&autoLogAppEvents=1"></script>
    <script src="${pageContext.request.contextPath}/js/facebook_oauth_main.js" charset="utf-8" defer onload="facebookInit()"></script>
    <%--  oauth api  --%>
    <script src="${pageContext.request.contextPath}/js/oauth_main.js" charset="utf-8" defer></script>

    <%--  style  --%>
    <link rel="stylesheet" href="https://getbootstrap.com/docs/4.1/assets/css/docs.min.css" rossorigin="anonymous"  async defer>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"  async defer>



    <style type="text/css">
        "${pageContext.request.contextPath}"
        /*#main_area{*/
        /*width: 850px;*/
        /*}*/

        /*#content_area{*/
        /*padding: 50px;*/
        /*height: 700px;*/
        /*z-index: 0;*/
        /*}*/

    </style>

</head>


<body>
<header>
    <div id="nav_area">
        <nav class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar">

            <a class="navbar-brand" href="${pageContext.request.contextPath}">SimpleBoard</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <c:if test="${cookie.mymymy eq null}">
                    <li class="nav-item active">
                        <a class="nav-link" href="login">login <span class="sr-only">(current)</span></a>
                    </li>
                    </c:if>
                    <c:if test="${cookie.mymymy ne null}">
                        <li class="nav-item">
                        <form id="logout" action="logout" method="post" enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <a class="nav-link signout" href="javascript:oauthLogout()">logout <span class="sr-only">logout</span></a>
                        </form>
                        </li>
                    </c:if>
                    <%--Spring Security Source--%>
                    <%--<sec:authorize access="isAnonymous()">--%>
                    <%--<li class="nav-item active">--%>
                        <%--<a class="nav-link" href="/login">login <span class="sr-only">(current)</span></a>--%>
                    <%--</li>--%>
                    <%--</sec:authorize>--%>
                    <%--<sec:authorize access="isAuthenticated()">--%>
                    <%--<li class="nav-item">--%>
                        <%--<form id="logout" action="/logout" method="post" enctype="application/x-www-form-urlencoded">--%>
                            <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
                            <%--<a class="nav-link" href='javascript:document.getElementById("logout").submit();'>logout <span class="sr-only">logout</span></a>--%>
                        <%--</form>--%>
                    <%--</li>--%>
                    <%--</sec:authorize>--%>
                </ul>
            </div>
        </nav>
    </div>
</header>
<div id="main_area" class="container bg-light"><%--START_DIV: main_area--%>
    <div id="content_area" class="container"><%--START_DIV: content_area--%>



