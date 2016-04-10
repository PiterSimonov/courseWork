<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/login.js" />"></script>
    <script src="<c:url value="/resources/js/roomScroll.js" />"></script>
    <title>Rooms</title>
</head>
<body>
<%@ include file="/WEB-INF/views/forms/loginForm.jsp" %>
<h1>Hotel: ${hotel.name}</h1>

<div id="services">
    Our services:<br/>
    <c:forEach items="${services}" var="service">
    - ${service.description}<br/>
</c:forEach> </div>
<h1>Choice rooms for booking</h1>
<input type="button" name="sortPrice" id="sortPrice" value="ByPriceAsc">
<input type="button" name="sortSeats" id="sortSeats" value="BySeatsAsc">
<h3>${message}</h3>
<form:form method="POST" action="/order/hotel/${hotel.id}/rooms" commandName="choice" id="choice-room">
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
                <td><form:checkbox path="roomsIds" value="${room.id}"></form:checkbox><div></div></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form:form>
</body>
</html>
