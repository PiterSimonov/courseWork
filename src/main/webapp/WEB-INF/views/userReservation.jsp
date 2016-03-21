<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/main.js" />"></script>
    <title>User Reservation</title>
</head>
<body>
User : ${user.id}
<div class="menuLink"><a href="/">Home</a></div>
<div class="menuLink"><a href="/logout">Logout</a></div>
<div class="right-panel">
    <a href="/profile/${user.id}/edit">Edit profile</a>
</div>
<div id="hotel-list"><h3>Your orders:</h3><br/>
    <c:forEach items="${orders}" var="order">
        <h6>Hotel: ${order.hotel.name}
            Price: ${order.price},
            date creation: <fmt:formatDate value="${order.creationTime}" pattern="yyyy-MM-dd"/>
            Status: ${order.status}
            <c:choose>
                <c:when test="${not order.commented and order.status == 'Confirmed'}">
                    <a href="/order/${order.id}/comment/${order.hotel.id}">Leave comment</a>
                </c:when>
                <c:when test="${order.status=='NotConfirmed'}">
                    <a href="/order/${order.id}/payment">Payment</a>
                </c:when>
            </c:choose>
            <br/>
        </h6>
        </c:forEach>
</div>
</body>
</html>
