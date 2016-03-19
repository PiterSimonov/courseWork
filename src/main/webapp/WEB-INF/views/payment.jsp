<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/animation.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Payment</title>
</head>
<body>
<form method="post" id="payment">
    <h3>User fills CARD Number and CVV code</h3>
    <input type="submit" value="Pay">
    <div id="loaderImage" style="display: none"><h4>Please wait...</h4><img
            src="${pageContext.request.contextPath}/resources/images/payment.gif"></div>
</form>
</body>
</html>
