<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hotel Booking Service</title>
</head>
<body>
<%@ include file="forms/loginForm.jsp"%>
<div class="right-panel">
    <div class="filter">
        <span><h5>Filtering Room</h5></span>
        <form id="filter-form" action="search" onsubmit="return checkDate()">
            <label for="city">City</label>
            <input id="city" type="text" name="city">
            <label for="hotel">Hotel</label>
            <input id="hotel" type="text" name="hotel">
            <label>Hotel Rank</label>
            <select form="filter-form" name="stars">
                <option></option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
            <label id="fromDate">From date</label>
            <input type="date" name="fromDate" class="date" data-date-split-input="true" required>
            <label id="toDate">To date</label>
            <input type="date" name="toDate" class="date" data-date-split-input="true" required>

            <label id="numOfTravelers">Number of travelers</label>
            <input type="number" name="numOfTravelers" min="1" max="4" required>
            <input type="submit" value="Search" name="searchSubmit">
        </form>
    </div>
</div>
<div id="hotel-list">
    <table class="table-hotels">
        <c:forEach items="${hotels}" var="hotel">
            <tr>
                <td>
                    <div>
                        <img class="hotel-img"
                             onerror="this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'"
                             src="${hotel.imageLink}">
                    </div>
                </td>
                <td>
                    <div>
                        <a href="/hotel/${hotel.id}">${hotel.name}</a> in ${hotel.city.name}
                    </div>
                    <div>Rating <fmt:formatNumber value="${hotel.rating}" maxFractionDigits="1"/>/5</div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
