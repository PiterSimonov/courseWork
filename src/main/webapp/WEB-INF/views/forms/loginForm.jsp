<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<link href="<c:url value='/resources/css/styles.css'/>" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
<script src="<c:url value="/resources/js/1.15.0_jquery.validate.js" />"></script>
<script src="<c:url value='/resources/js/validator.js'/>"></script>

<div id="head">
    <div id="user-bar" class="user-bar">
    <c:choose>
        <c:when test="${user.role == 'NotAuthorized' || user.role == null }">
            <a class="menuLink" href="#" id="signIn">Sign In</a>
            <a class="menuLink" id="showpopup" href="#">Log In</a>
        </c:when>
        <c:otherwise>
            <a class="menuLink" href="/profile">Profile</a>
            <a class="menuLink" href="/logout">Logout</a>
        </c:otherwise>
    </c:choose>
</div>
<div id="popup">
    <div>
        <form:form method="POST" commandName="user" action="check-user" id="loginForm" autocomplete="false">
            <h4>Enter to the site<br/></h4>
            <form:label path="login">Login:</form:label><br/>
            <form:input path="login"/>
            <form:errors path="login" cssClass="error"/><br/>
            <form:label path="password">Password:</form:label><br/>
            <form:password path="password"/>
            <form:errors path="password" cssClass="error"/><br/>
            <input type="submit" id="btnLogin" value="Login">
            <br>
            <a href="registration" id="registration" name="registration">Registration</a>
            <span id="error-box"></span>
        </form:form>
    </div>
    <div class="close"></div>
</div>
    <div id="register" class="register">
        <form:form method="POST" commandName="user" action="registration" id="registerForm">
            <fieldset class="boxBody">
                <form:label path="login">Login:</form:label><br/>
                <form:input path="login" cssClass="required"/>
                <div id="login-free" style="display: inline"></div>
                <form:errors path="login" cssClass="error"/><br/>

                <form:label path="firstName">First Name:</form:label><br/>
                <form:input path="firstName"/>
                <form:errors path="firstName" cssClass="error"/><br/>

                <form:label path="lastName">Last Name:</form:label><br/>
                <form:input path="lastName"/>
                <form:errors path="lastName" cssClass="error"/><br/>

                <form:label path="phone">Phone:</form:label><br/>
                <form:input path="phone" cssClass="required"/>
                <form:errors path="phone" cssClass="error"/><br/>

                <form:label path="email">E-Mail:</form:label><br/>
                <form:input path="email"/>
                <form:errors path="email" cssClass="error"/><br/>
                <form:label path="password">Password:</form:label><br/>
                <form:password path="password" cssClass="required"/>
                <form:errors path="password" cssClass="error"/><br/><br/>
                <form:select path="role">
                    <form:option value="CLIENT">Client</form:option>
                    <form:option value="HotelOwner">Hotel Owner</form:option>
                </form:select>

                <input type="submit" id="btnLogin" value="Register">
            </fieldset>
        </form:form>

    </div>
<div id="back"></div>
</div>