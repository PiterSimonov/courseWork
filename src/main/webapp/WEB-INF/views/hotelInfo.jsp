<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/1.15.0_jquery.validate.js" />"></script>
    <script src="<c:url value="/resources/js/dataBookingSet.js" />"></script>
    <script src="<c:url value="/resources/js/login.js" />"></script>
    <script src="<c:url value="/resources/js/accordion.js" />"></script>
    <script src="<c:url value="/resources/js/hotelInfoPage.js" />"></script>
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <title>Hotel Info</title>
</head>
<body>
<%@ include file="forms/loginForm.jsp" %>
<div id="hotel-info">
    <input id="id" value="${hotel.id}" hidden>
    <div style="display: inline-block">Hotel : ${hotel.name}</div>
    <span id="image-stars"><c:forEach begin="1" end="${hotel.stars}">
        <img class="stars" src="${pageContext.request.contextPath}/resources/images/hotels/stars.png"/>
    </c:forEach></span><br/>
    <img id="hotel-image" src="${hotel.imageLink}" style="width: 500px">
</div>
<div>
    Our services:<br/>
    <div id="services">
        <c:forEach items="${hotelServices}" var="service">
            - ${service.description}<br/>
        </c:forEach></div>
</div>
<div>
    <div class="main">
        <table id="table-rooms">
            <c:forEach items="${rooms}" var="room">
                <tr class="room-table">
                    <td>
                        <img onerror="this.onerror=null;this.src='/resources/images/rooms/noImage.jpg'"
                             src=${room.imageLink}>
                    </td>
                    <td>
                        <a href="${hotel.id}/roomDetails/${room.id}">Room № ${room.number}</a><br/>
                        Type is ${room.type}<br/>
                        Places: ${room.seats}<br/>
                        Price: ${room.price} &#36;
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<c:choose>
    <c:when test="${user.role == 'HotelOwner' and hotel.user.id eq user.id}">
        <div class="right-panel">

            <div class="accordion">
                <h4 class="active">Add ROOM</h4>
                <div style="display: block;">
                    <form id="add-room" method="post" action="/addRoom" enctype="multipart/form-data"
                          autocomplete="off">
                        <input id="roomNumber" name="number" type="number" placeholder="№" required><br/>
                        <input id="roomType" type="text" name="type" placeholder="Type" required><br/>
                        <input id="roomPrice" type="number" min="1" name="price" placeholder="Price" required><br/>
                        <textarea id="roomDescription" name="description" placeholder="Description"
                                  maxlength="255"></textarea><br/>
                        <label for="roomPlaces">Seats: </label>
                        <input id="roomPlaces" name="seats" type="number" min="1" max="8"
                               required><br/>
                        <input type="file" name="imageFile" id="imageFile" accept="image/*" style="width: 250px"><br/>
                        <input type="hidden" name="hotelId" id="hotelId" value="${hotel.id}">
                        <input id="roomSubmit" type="submit" value="Add Room"><span id="waitRoom"></span>
                    </form>
                </div>
                <h4>Edit Hotel</h4>
                <div style="display: none;">
                    <form id="edit-hotel" method="post" enctype="multipart/form-data" autocomplete="off">
                        <label for="hotelName">Name: </label>
                        <input id="hotelName" type="text" name="name" required value="${hotel.name}"><br/>
                        <label>Stars: </label>
                        <select form="edit-hotel" name="stars" required><br/>
                            <option value="${hotel.stars}">${hotel.stars}</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select><br/>
                        <label>Services:</label>
                        <select form="edit-hotel" name="convenience" size="5" multiple required>
                            <c:forEach items="${services}" var="service">
                                <option value="${service.id}">${service.description}</option>
                            </c:forEach>
                        </select><br/>
                        <input type="file" name="imageFile" id="hotelImage" accept="image/*" style="width: 250px">
                        <input type="hidden" name="hotelId" value="${hotel.id}">
                        <br/><input id="hotelSubmit" type="submit" value="Save"><span id="wait"></span>
                    </form>
                </div>
                <h4>Booking</h4>
                <div style="display: none;">
                    <form method="get" action="${hotel.id}/getBooking" id="hotel-booking">
                        <label for="fromDate">From: </label>
                        <input type="date" name="fromDate" id="fromDate" class="booking-date"
                               data-date-split-input="true" required><br/>
                        <label for="endDate">To: &nbsp;&nbsp;&nbsp;</label>
                        <input type="date" name="toDate" id="endDate" class="booking-date" data-date-split-input="true"
                               required><br/>
                        <label for="roomNumber">Room №: </label>
                        <input type="number" min="1" name="roomNumber" id="number" style="width: 50px"><br/>
                        <input type="submit" value="Get booking">
                    </form>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>
</body>
</html>
