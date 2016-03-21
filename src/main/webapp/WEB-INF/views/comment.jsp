<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Comment</title>
</head>
<body>
User : ${user.id}
<form id="comment-form" method="post">
    <label for="comment-text">Comment:</label><br/>
    <textarea id="comment-text" name="comment" rows="10" cols="30" maxlength="255" style="resize: none"></textarea>
    <label for="rating">Rating:</label>
    <input id="rating" type="number" name="rating" min="1" max="5" required>
    <input type="submit" name="Save" value="SAVE">
</form>
</body>
</html>
