package com.todolist.cotroller.user;

import com.todolist.cotroller.utils.Views;
import com.todolist.model.Todo;
import com.todolist.model.User;
import com.todolist.model.api.ServiceDaoHelper;
import com.todolist.model.api.TodoDaoException;
import com.todolist.model.api.TodoRepository;
import com.todolist.model.utils.TodoListUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet that controls action on the user's home page.
 */

@WebServlet(name = "HomeServlet", urlPatterns = "/todos")
public class HomeServlet extends HttpServlet {

    private TodoRepository todoService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        todoService = ServiceDaoHelper.getTodoRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);

        try {
            List<Todo> todoList = todoService.getTodoListByUser(user.getId());

            request.setAttribute("todoList", todoList);
            request.setAttribute("homeTabStyle", "active");

            int totalCount = todoList.size();
            int doneCount = TodoListUtils.countTotalDone(todoList);
            int todoCount = totalCount - doneCount;
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("doneCount", doneCount);
            request.setAttribute("todoCount", todoCount);

            request.getRequestDispatcher(Views.HOME_PAGE).forward(request, response);
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
