<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="utf-8" %>

<%@ include file="header.jsp"%>


<div class="container">
    <h3>login Form</h3>

    <form action="login" method="post">
        <div class="form-group">
            <label >ID : </label>
            <input type="text" class="form-control" placeholder="Enter ID" name="userId">
        </div>
        <div class="form-group">
            <label >PW : </label>
            <input type="password" class="form-control" placeholder="Enter PW" name="password">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="text-center form-group">
            <button type="submit" class="btn btn-primary">보내기</button>
            <a class="btn btn-primary text-white" href="list">취소</a>
            <a class="btn btn-danger text-white" href="signup">회원가입</a>
        </div>
    </form>
    <div id="social_login" style="text-align: center">
        <form id="snslogin" action="snslogin" method="post">
            <input id="sns_type" type="hidden" name="sns_type" >
            <input id="idtoken" type="hidden" name="idtoken" >
        </form>
        <div id="my-signin2" style="display: inline-block;" ></div>
        <div id="fb-root"></div>
        <div class="fb-login-button" data-onlogin="javascript:facebookLogin()" data-width="" data-size="large" data-button-type="continue_with" data-auto-logout-link="false" data-use-continue-as="false"></div>
        <div>

            <a href="${naver_oauth}" target="_self"><img style="width:20%; margin: 5px;" src="/img/btn/naver_account_login_narrow_green.png"></a>
        </div>
        <div>
            <a href="${kakao_oauth}" target="_self"><img style="width:20%; margin:5px;" src="/img/btn/kakao_account_login_btn_medium_narrow.png"></a>
        </div>
    </div>

</div>

<%@include file="footer.jsp"%>