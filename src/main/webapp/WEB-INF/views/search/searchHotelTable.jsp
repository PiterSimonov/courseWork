<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <span id="image-stars"><c:forEach begin="1" end="${hotel.stars}">
                        <img class="stars" src="/resources/images/hotels/stars.png"/>
                    </c:forEach></span>
            <div>
                <a href="/search/hotel/${hotel.id}/rooms">${hotel.name}</a> in ${hotel.city.name}
            </div>
            <div>Rating <fmt:formatNumber value="${hotel.rating}" maxFractionDigits="1"/>/5</div>
            <div>
                <a href="#?w=700" rel="popup_name" class="poplight" id="${hotel.id}">Comments</a>
            </div>
        </td>
    </tr>
</c:forEach>