<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Comment</title>
</head>
<body>
<form id="comment-form" method="post">
    <label for="comment-text">Comment:</label>
    <textarea id="comment-text" name="comment"></textarea>
    <label for="rating">Rating:</label>
    <input id="rating" type="number" name="rating" min="1" max="5">
    <input type="submit" name="Save" value="SAVE">
</form>
</body>
</html>
