package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private List<Meal> meals;

    private static final Logger log = getLogger(MealServlet.class);

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        meals = TEST_MEALS_LIST;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        request.setAttribute("mealsTo", getMealsTo(meals, TEST_CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
