
package com.todolist.cotroller.todo;


import com.todolist.cotroller.utils.Views;
import com.todolist.model.Priority;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet that controls todo creation.
 */

@WebServlet(name = "CreateTodoServlet", urlPatterns = {"/todos/new", "/todos/new.do"})
public class CreateTodoServlet extends HttpServlet {

    private TodoRepository todoService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        todoService = ServiceDaoHelper.getTodoRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("today", new SimpleDateFormat(TodoListUtils.DATE_FORMAT).format(new Date()));
        request.getRequestDispatcher(Views.CREATE_TODO_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);

        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
        String priority = request.getParameter("priority");

        try {
            Todo todo = new Todo(user.getId(), title, false, Priority.valueOf(priority), new Date(dueDate));
            todoService.create(todo);
            request.getRequestDispatcher("/todos").forward(request, response);
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        }

    }

}
