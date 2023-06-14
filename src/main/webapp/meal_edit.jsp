<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }

        h2, h3 {
            margin: 15px 0 5px;
        }
    </style>
    <title></title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${action.equals('add') ? "Add meal" : "Edit meal"}</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Date:</dt>
        <dd><input type="datetime-local" name="dateTime" size=55 value="${meal.dateTime}"></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" name="description" size=55 value="${meal.description}"></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" name="calories" size=20 value="${meal.calories}"></dd>
    </dl>
    <button style="width: 70px" type="submit">Save</button>
    <button style="width: 70px" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>