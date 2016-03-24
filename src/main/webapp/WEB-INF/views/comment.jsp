<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Comment</title>
</head>
<body>
<form id="comment-form" method="post">
    <fieldset class="boxBody">
    <label for="comment-text">Comment:</label><br/>
    <textarea id="comment-text" name="comment" rows="10" cols="30" maxlength="255"></textarea>
    <label for="rating">Rating:</label>
    <input id="rating" type="number" name="rating" min="1" max="5" required>
    <input type="submit" name="Save" value="SAVE">
    </fieldset>
</form>
</body>
</html>
