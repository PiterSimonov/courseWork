<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Rooms</title>
</head>
<body>
<%@ include file="/WEB-INF/views/forms/loginForm.jsp" %>
<h1>Payment page</h1>
User : ${user.id}
<form:form method="POST" commandName="choice" id="choice-room">
    <table>
        <tbody>
        <c:forEach items="${rooms}" var="room">
            <tr>
                <td>Room ${room.number}</td>
                <td>Price ${room.price}</td>
                <td><form:checkbox path="roomsIds" value="${room.id}"></form:checkbox></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3">
                <input form="choice-room" type="submit" value="Submit">
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</body>
</html>
