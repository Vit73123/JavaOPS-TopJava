<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
            margin-right: -15px;
            margin-left: -15px;
        }

        .col {
            flex: 0 0 15%;
        }

        .form-control {
            display: block;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>

    <div>
        <div style="padding: 1.25rem">
            <form id="filter" method="get" action="meals">
                <input type="hidden" name="action" value="filter"/>
                <div class="row">
                    <div class="col">
                        <label for="startDate">Date from</label>
                        <input class="form-control" type="date" name="startDate" id="startDate"/>
                    </div>
                    <div class="col">
                        <label for="endDate">Date to</label>
                        <input class="form-control" type="date" name="endDate" id="endDate"/>
                    </div>
                    <div class="col">
                        <label for="startTime">Time from</label>
                        <input class="form-control" type="time" name="startTime" id="startTime"/>
                    </div>
                    <div class="col">
                        <label for="endTime">Time to</label>
                        <input class="form-control" type="time" name="endTime" id="endTime"/>
                    </div>
                </div>
                <br/>
                <div>
                    <button type=submit style="margin-left: -15px; width: 70px">
                        Filter
                    </button>
                </div>
                <br/>
            </form>
        </div>
    </div>

    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>