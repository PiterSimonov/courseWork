<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
    <script src="<c:url value="/resources/js/login.js" />"></script>
    <script src="<c:url value="/resources/js/accordion.js" />"></script>
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <title>Room info</title>
</head>
<body>
<%@ include file="forms/loginForm.jsp" %>
<div class="right-panel">
    <c:choose>
        <c:when test="${hotel.user.id == user.id}">
            <div class="accordion">
                <h4>Edit Room</h4>
                <div style="display: none;">
                    <form id="edit-room" method="post" enctype="multipart/form-data" autocomplete="off">
                        <table>
                            <tr>
                                <td><label for="roomType">Type: </label></td>
                                <td><input id="roomType" type="text" name="type" value="${room.type}" required></td>
                            </tr>
                            <tr>
                                <td><label for="roomPrice">Price: </label></td>
                                <td><input id="roomPrice" type="number" min="1" name="price" value="${room.price}" required></td>
                            </tr>
                            <tr>
                                <td><label for="roomDescription">Description: </label></td>
                                <td><textarea id="roomDescription" name="description"
                                              maxlength="255">${room.description}</textarea></td>
                            </tr>
                            <tr>
                                <td><label for="roomPlaces">Seats: </label></td>
                                <td><input id="roomPlaces" name="seats" type="number" min="1" max="8" value="${room.seats}"
                                           required></td>
                            </tr>
                            <tr>
                                <td colspan="2"><input type="file" name="imageFile" id="roomImage" accept="image/*"
                                                       style="width: 250px">
                                </td>
                                <input type="hidden" name="roomId" value="${room.id}">
                            </tr>
                        </table>
                        <input id="roomSubmit" type="submit" value="Save"><span id="wait"></span>
                    </form>
                </div>
            </div>
        </c:when>
    </c:choose>
</div>
<div id="hotel-info">
    <p>Hotel : ${hotel.name}
        <c:forEach begin="1" end="${hotel.stars}"><img class="stars"
                                                       src="/resources/images/hotels/stars.png"/></c:forEach>
    </p>
</div>
<p>Room № ${room.number}</p>
<p>Type: <span id="type">${room.type}</span></p>
<p>Seats: <span id="seats">${room.seats}</span></p>
<p>Price: <span id="price">${room.price}</span></p>
<p>Description:</p>
<span id="description">${room.description}</span><br/>
<span id="image"><img src='${room.imageLink}'
                      onerror="this.onerror=null;this.src='/resources/images/rooms/noImage.jpg'">
</span>
<div class="booking-div"></div>
<br/>
<div class="main">
    <form id="form-booking" method="post">
        <input type="date" name="fromDate" id="fromDate" class="date" data-date-split-input="true" required><br/>
        <input type="date" name="toDate" id="toDate" class="date" data-date-split-input="true" required><br/>
        <input name="roomId" id="roomId" value="${room.id}" hidden>
        <input name="userRole" id="userRole" value="${user.role}" hidden>
        <input type="submit" value="Book">
    </form>
    <div id="is-free"></div>
</div>
</body>
</html>
