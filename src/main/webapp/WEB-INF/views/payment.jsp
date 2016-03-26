<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="<c:url value="/resources/js/animation.js" />"></script>
    <title>Payment</title>
</head>
<body>
<form method="post" id="payment">
    <h4>User fills CARD Number and CVV code</h4>
    <input type="submit" id="pay-button" value="Pay">
    <div id="loaderImage" style="display: none"><h4>Please wait...</h4><img
            src="${pageContext.request.contextPath}/resources/images/payment.gif"></div>
</form>
</body>
</html>
