<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
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
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>View meal</h2>
<dl>
    <dt>Date Time:</dt>
    <dd>${TimeUtil.format(meal.dateTime)}</dd>
</dl>
<dl>
    <dt>Description:</dt>
    <dd>${meal.description}</dd>
</dl>
<dl>
    <dt>Calories:</dt>
    <dd>${meal.calories}</dd>
</dl>
<button style="width: 70px" onclick="window.history.back()">Ok</button>
</body>
</html>