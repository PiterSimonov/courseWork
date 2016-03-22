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
        <tbody id="room-list">
        <c:forEach items="${rooms}" var="room">
            <tr>
                <td>
                    <img onerror="this.onerror=null;this.src='/resources/images/rooms/noImage.jpg'"
                         src=${room.imageLink}>
                </td>
                <td>Room № ${room.number}<br/>
                    Type is ${room.type}<br/>
                    Places: ${room.seats}<br/>
                    Price: ${room.price} &#36;
                </td>
                <td><form:checkbox path="roomsIds" value="${room.id}"></form:checkbox></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <input form="choice-room" type="submit" value="Submit">
</form:form>
</body>
</html>
