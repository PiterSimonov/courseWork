<div class="user-bar">
    <c:choose>
        <c:when test="${user.role == 'NotAuthorized' || user.role == null }">
            <a class="menuLink" href="/registration">Sign In</a>
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
        <form:form method="POST" commandName="user" action="check-user" id="loginForm">
            <h4>Enter to the site<br/></h4>
            <form:label path="login">Login:</form:label><br/>
            <form:input path="login"/>
            <form:errors path="login" cssClass="error"/><br/>
            <form:label path="password">Password:</form:label><br/>
            <form:password path="password"/>
            <form:errors path="password" cssClass="error"/><br/>
            <input type="submit" id="btnLogin" value="Login">
            <span id="error-box"></span>
        </form:form>
    </div>
    <div class="close">[X]</div>
</div>

<div id="back"></div>