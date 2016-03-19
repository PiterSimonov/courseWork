<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hotel Info</title>
</head>
<body>
<%@ include file="forms/loginForm.jsp" %>
<div id="hotel-info">
    <p>Hotel : ${hotel.name}
        <c:forEach begin="1" end="${hotel.stars}"><img class="stars"
                                                       src="${pageContext.request.contextPath}/resources/images/hotels/stars.png"/></c:forEach>

</div>
<div class="right-panel">
</div>
<div>
    <div class="main">
        <table>
            <%--<c:forEach items="${hotel.rooms}" var="room">--%>
            <c:forEach items="${rooms}" var="room">
                <tr class="room-table">
                    <td>
                        <img onerror="this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'"
                            <%--src="/resources/images/rooms/${hotel.name}${room.id}.jpg"--%>
                             src=${room.imageLink}
                        >
                    </td>
                    <td>
                        <a href="${hotel.id}/roomDetails/${room.id}">Room № ${room.number}</a><br/>
                        Type is ${room.type}<br/>
                        Places: ${room.seats}<br/>
                        Price: ${room.price} &#36;
                    </td>
                    <td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
${user.role} role, user id ${user.id}
<p>${hotel.user.id}Hotel owner id</p>
<c:choose>
    <c:when test="${user.role == 'HotelOwner' and hotel.user.id eq user.id}">
        <div class="add-room-div">
            <form id="add-room" method="post" action="/addRoom" enctype="multipart/form-data">
                <input id="roomNumber" name="number" type="number" placeholder="№" required><br/>
                <input id="roomType" type="text" name="type" placeholder="Type" required><br/>
                <input id="roomPrice" type="number" name="price" placeholder="Price" required><br/>
                <textarea id="roomDescription" name="description" placeholder="Description"></textarea><br/>
                <input id="roomPlaces" name="seats" placeholder="Seats" type="number" min="1" max="4" required><br/>
                <input type="file" name="image"><br/>
                <input type="hidden" name="hotelId" value="${hotel.id}">
                <input id="roomSubmit" type="submit" value="Add Room">
            </form>
        </div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
</body>
</html>
