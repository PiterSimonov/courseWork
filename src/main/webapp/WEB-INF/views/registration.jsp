<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/validator.js'/>"></script>
    <link href="<c:url value='/resources/css/styles.css'/>" rel="stylesheet">
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hotel Booking</title>
</head>
<body>
<h3>Hotel Booking Services REGISTRATION</h3>
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
</body>
</html>
