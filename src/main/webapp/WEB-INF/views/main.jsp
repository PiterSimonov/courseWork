<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/1.15.0_jquery.validate.js" />"></script>
    <script src="<c:url value="/resources/js/dateSetter.js" />"></script>
    <script src="<c:url value="/resources/js/searchForm.js" />"></script>
    <script src="<c:url value="/resources/js/comments.js" />"></script>
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <script src="<c:url value="/resources/js/login.js" />"></script>
    <title>Hotel Booking Service</title>
</head>
<body>
<%@ include file="forms/loginForm.jsp"%>
<%@ include file="forms/filterForm.jsp"%>
<div id="hotel-list">
    <table class="table-hotels" id="hotelsList">
        <%@ include file="forms/hotelTable.jsp"%>
    </table>
    <div id="more-div"></div>
</div>

<div id="popup_name" class="popup_block">
    <div id="commentsWindow" style="width: 700px; max-height: 400px; overflow-y: scroll">
    </div>
</div>
</body>
</html>
