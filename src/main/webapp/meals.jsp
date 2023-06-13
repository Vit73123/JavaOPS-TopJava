<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="">Add Meal</a></p>
<div>
    <table border="1" bordercolor="black" cellpadding="8" cellspacing="0">
        <tr >
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${mealsTo}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color: ${mealTo.excess ?  'red' : 'green'}">
                <td>
                        ${TimeUtil.format(mealTo.dateTime)}
                </td>
                <td>
                        ${mealTo.description}
                </td>
                <td>
                        ${mealTo.calories}
                </td>
                <td>
                    <a href="">Update</a>
                </td>
                <td>
                    <a href="">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>