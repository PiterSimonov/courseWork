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
    <title>Room info</title>
</head>
<body>
<%@ include file="forms/loginForm.jsp" %>
<div class="right-panel">
</div>
<div id="hotel-info">
    <p>Hotel : ${hotel.name}
        <c:forEach begin="1" end="${hotel.stars}"><img class="stars"
                                                       src="/resources/images/hotels/stars.png"/></c:forEach>
    </p>
</div>
<p>Room № ${room.number}</p>
<p>Description:</p>
<span>${room.description}<br/></span>
<img src=${room.imageLink}>

<div class="booking-div"></div><br/>
<div class="main">
    <form id="form-booking" method="post">
        <input type="date" name="fromDate" id="fromDate" class="date" data-date-split-input="true" required><br/>
        <input type="date" name="toDate" id="toDate" class="date" data-date-split-input="true" required><br/>
        <input name="roomId" id="roomId" value="${room.id}" hidden>
        <input name="userRole" id="userRole" value="${user.role}" hidden>
        <input type="submit" value="Booking">
    </form>
    <div id="is-free"></div>
</div>
</body>
</html>
