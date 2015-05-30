package com.todolist.cotroller.todo;

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
 * Servlet that controls todo list search.
 */

@WebServlet(name = "SearchServlet", urlPatterns = "/todos/search")
public class SearchServlet extends HttpServlet {

    private TodoRepository todoService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        todoService = ServiceDaoHelper.getTodoRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);
        try {
            List<Todo> todoList = todoService.searchTodoListByTitle(user.getId(), title);
            request.setAttribute("todoList", todoList);
            request.getRequestDispatcher(Views.SEARCH_PAGE).forward(request, response);
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        }

    }

}
