package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Main;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.Main.TEST_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.Main.testMapStorage;
import static ru.javawebinar.topjava.util.MealsUtil.getAllMealsTo;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Storage storage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = testMapStorage;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        final boolean isCreate = (id == null || id.length() == 0);

        Meal meal;
        if (isCreate) {
            meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")),
                    Main.testMapStorageMaxId.getAndIncrement()
            );
        } else {
            meal = storage.get(Integer.valueOf(id));
            meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
            meal.setDescription(request.getParameter("description"));
            meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        }

        if (isCreate) {
            storage.save(meal);
        } else {
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String id = request.getParameter("id");
        Integer id_num = (id == null ? 0 : Integer.valueOf(id));
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("meals", getAllMealsTo(storage.getAll(), TEST_CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "view":
                meal = storage.get(id_num);
                break;
            case "add":
                meal = Meal.EMPTY;
                break;
            case "edit":
                meal = new Meal(
                        storage.get(id_num).getDateTime(),
                        storage.get(id_num).getDescription(),
                        storage.get(id_num).getCalories(),
                        storage.get(id_num).getId()
                );
                break;
            case "delete":
                storage.delete(id_num);
                response.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("meal", meal);
        request.setAttribute("action", action);
        request.getRequestDispatcher(
                        action.equals("view") ? "/meal.jsp" : "/meal_edit.jsp")
                .forward(request, response);
    }
}
