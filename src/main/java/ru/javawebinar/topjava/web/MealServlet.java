package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Storage storage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = TEST_MEALS_MAP_STORAGE;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet Post");

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        final boolean isCreate = (id == null || id.isEmpty());

        Meal meal = new Meal(
                isCreate ? 0 : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (isCreate) {
            storage.create(meal);
        } else {
            storage.update(meal);
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet Get");

        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("meals", getAllMealsTo(storage.getAll(), TEST_CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "view":
            case "edit":
                meal = storage.get(Integer.parseInt(id));
                break;
            case "add":
                meal = MealsUtil.EMPTY;
                break;
            case "delete":
                storage.delete(Integer.parseInt(id));
            default:
                response.sendRedirect("meals");
                return;
        }

        request.setAttribute("meal", meal);
        request.setAttribute("action", action);
        request.getRequestDispatcher(
                        action.equals("view") ? "/meal.jsp" : "/meal_edit.jsp")
                .forward(request, response);
    }
}
