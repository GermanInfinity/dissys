<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Chioma_N
  Date: 4/21/22
  Time: 1:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h1>Select a video to upload and play</h1>
<form method="post" action="http://52.2.76.100:8000/file_transfer" enctype="multipart/form-data">
    <dl>
        <p>
            <input type="hidden" id="user" name="user" value=<%= request.getAttribute("user") %>>
            <input type="file" name="file" autocomplete="off" required>
        </p>
    </dl>
    <p>
    </p>
    <label for="style">Choose a style:</label>
    <select name="style" id="style">
        <option value="candy">Candy</option>
        <option value="mosaic">Mosaic</option>
        <option value="rain_princess">Rain princess</option>
        <option value="udnie">Udnie</option>
    </select>
    <br><br>
    <input type="submit" value="Upload">

    <button onclick="location.href = '../index/'">Sign out</button>  <%--    FIX THIS--%>

</form>
<br><br>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <label for="vids">Your videos:</label>
    <select id="vids" name="vids" multiple>
    <c:forEach items="${videos}" var="entry">
        <option value="${entry}"><c:out value="${entry.value}"/></option>
    </c:forEach>
    </select>
    <br><br>

    <button onclick="location.href = 'http://127.0.0.1:5000/file_transfer'">Download</button>

</body>
</html>
