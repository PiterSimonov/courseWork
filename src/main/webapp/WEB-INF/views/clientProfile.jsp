<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/deleteOrders.js" />"></script>
    <title>User Reservation</title>
</head>
<body>
<div class="menuLink"><a href="/">Home</a></div>
<div class="menuLink"><a href="/logout">Logout</a></div>
<div class="left-panel">
    <a href="/profile/${user.id}/edit">Edit profile</a>
</div>
<div class="orders"><h3>Your orders:</h3><br/>
    <c:forEach items="${orders}" var="order">
        <div id="order${order.id}" style="border: solid rgba(180, 136, 255, 0.42); margin-bottom: 5px">
            <h4>Hotel: ${order.hotel.name}
                Price: ${order.price},
                date creation: <fmt:formatDate value="${order.creationTime}" pattern="yyyy-MM-dd HH:mm"/>
                Status: ${order.status}
            </h4>
            <c:choose>
                <c:when test="${not order.commented and order.status == 'Confirmed'}">
                    <a href="/order/${order.id}/comment/${order.hotel.id}">Leave comment</a>
                </c:when>
                <c:when test="${order.status=='NotConfirmed'}">
                    <a href="/order/${order.id}/payment">Payment</a>
                    <a class="delete-order" href="${order.id}">[Cancel]</a>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
</div>
</body>
</html>
