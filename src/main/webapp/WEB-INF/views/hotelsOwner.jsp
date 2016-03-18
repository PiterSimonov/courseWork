<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <title>Hotel owner</title>

</head>
<body>
<div class="menuLink"><a href="/">Home</a></div>
${user.id}
<div id="hotel-list">
    <table class="table-hotels">
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
        <div class="addHotel">
            <form id="add-hotel" method="post" action="addHotel" enctype="multipart/form-data">
                <input id="hotelName" type="text" name="name" placeholder="Hotel Name" required>
                <input id="hotelCity" type="text" name="city" placeholder="City" required>
                <select form="add-hotel" name="stars" required>
                    <option></option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                <input type="file" name="image" accept="image/jpeg">
                <input name="owner" type="number" value="${user.id}" hidden> <%--!!!!!!!!!--%>
                <input id="hotelSubmit" type="submit" value="Add Hotel">
            </form>
        </div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
</body>
</html>
