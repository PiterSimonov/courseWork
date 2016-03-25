<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Booking</title>
</head>
<body>

<c:choose>
    <c:when test="${fn:length(bookings)>0}">
        <table id="bookings">
            <th>Room №</th>
            <th>Date from</th>
            <th>Date to</th>
            <th>Order status</th>
            <c:forEach items="${bookings}" var="booking">
                <tr>
                    <td>${booking.room.number}</td>
                    <td>${booking.startDate}</td>
                    <td>${booking.endDate}</td>
                    <td>${booking.order.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <h3>Nope booking for these dates</h3>
    </c:otherwise>
</c:choose>
</body>
</html>
