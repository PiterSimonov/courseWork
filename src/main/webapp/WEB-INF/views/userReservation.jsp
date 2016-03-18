<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <title>User Reservation</title>
</head>
<body>
<div id="hotel-list">
    <c:forEach items="${orders}" var="order">
        <h3>Order price: ${order.price}, date creation: <fmt:formatDate value="${order.creationTime}"
                                                                        pattern="yyyy-MM-dd"/></h3>
        <p>
        </p>
        </c:forEach>
</div>


</body>
</html>
