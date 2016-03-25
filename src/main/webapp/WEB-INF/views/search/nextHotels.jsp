<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
