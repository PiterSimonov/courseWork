<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/1.15.0_jquery.validate.js" />"></script>
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <title>Edit Profile</title>
</head>
<body>
<div>
    <form:form commandName="user" id="edit-user-form" method="post">
        <form:label path="firstName">First Name:</form:label><br/>
        <form:input path="firstName"/><br/>

        <form:label path="lastName">Last Name:</form:label><br/>
        <form:input path="lastName"/><br/>

        <form:label path="phone">Phone:</form:label><br/>
        <form:input path="phone" cssClass="required"/>
        <form:errors path="phone" cssClass="error"/><br/>
        <input type="submit" name="Update" value="Save">
    </form:form>
</div>
</body>
</html>
