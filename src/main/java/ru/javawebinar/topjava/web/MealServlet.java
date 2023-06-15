package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealsLocalStorage;
import ru.javawebinar.topjava.storage.MealsStorage;
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

    private MealsStorage mealsStorage = new MealsLocalStorage();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        for(Meal meal : testMealsList) {
            mealsStorage.create(meal);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        final boolean isCreate = (id == null || id.isEmpty());

        Meal meal = new Meal(
                isCreate ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (isCreate) {
            mealsStorage.create(meal);
            log.debug("Post: Create Meal (id: {})", + meal.getId());
        } else {
            mealsStorage.update(meal);
            log.debug("Post: Update Meal (id: {})", + meal.getId());
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("Get: View meals");
            request.setAttribute("meals", getAllMealsTo(mealsStorage.getAll(), TEST_CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "view":
            case "edit":
                meal = mealsStorage.get(Integer.parseInt(id));
                log.debug("Get: {} Meal(id: {})", action.equals("view") ? "View" : "Edit", id);
                break;
            case "add":
                log.debug("Get: Create Meal");
                meal = MealsUtil.EMPTY;
                break;
            case "delete":
                log.debug("Get: Delete Meal(id: {})", id);
                mealsStorage.delete(Integer.parseInt(id));
            default:
                log.debug("Get: Illegal action");
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
