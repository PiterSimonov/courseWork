<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.10.2.js" />"></script>
    <script src="<c:url value="/resources/js/1.15.0_jquery.validate.js" />"></script>
    <script src="<c:url value="/resources/js/validator.js" />"></script>
    <script src="<c:url value="/resources/js/saveHotel.js" />"></script>
    <title>Hotel owner</title>
</head>
<body>
<a class="menuLink" href="/">Home</a>
<div id="hotel-list">
    <table class="table-hotels" id="table-hotels">
        <c:forEach items="${hotels}" var="hotel">
            <tr>
                <td>
                    <div>
                        <img class="hotel-img"
                             onerror="this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'"
                             src=${hotel.imageLink}>
                    </div>
                </td>
                <td>
                    <span id="image-stars"><c:forEach begin="1" end="${hotel.stars}">
                        <img class="stars" src="${pageContext.request.contextPath}/resources/images/hotels/stars.png"/>
                    </c:forEach></span>
                    <div>
                        <a href="/hotel/${hotel.id}">${hotel.name}</a> in ${hotel.city.name}
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<c:choose>
    <c:when test="${user.role == 'HotelOwner'}">
        <div class="right-panel">
            <div class="addHotel">
                <form id="add-hotel" method="post" enctype="multipart/form-data" autocomplete="off">
                    <input id="hotelName" type="text" name="name" placeholder="Hotel Name" required><br/>
                    <label>Country: </label>
                    <select form="add-hotel" name="country_id" id="country_id" required>
                        <option value="">-Country-</option>
                        <c:forEach items="${countries}" var="country">
                            <option value="${country.id}">${country.name}</option>
                        </c:forEach>
                    </select><br/>
                    <label>City:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </label>
                    <select form="add-hotel" name="city_id" id="city_id" disabled required>
                        <option value="">- City -</option>
                    </select><br/>
                    <label>Stars: </label>
                    <select id="stars" form="add-hotel" name="stars" required><br/>
                        <option></option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select><br/>
                    <label>Services:</label>
                    <div id="conveniences" style="border: 1px solid #C0C0C0">
                        <c:forEach items="${services}" var="service">
                            <input type="checkbox" name="option1" value="${service.id}">${service.description}<br>
                        </c:forEach>
                    </div>
                    <br/>
                    <input type="file" name="imageFile" id="image" accept="image/*" style="width: 250px">
                    <br/><input id="hotelSubmit" type="submit" value="Add Hotel"><span id="wait"></span>
                </form>
            </div>
        </div>
    </c:when>
</c:choose>

</body>
</html>
