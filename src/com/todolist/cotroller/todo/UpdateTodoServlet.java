
package com.todolist.cotroller.todo;

import com.todolist.cotroller.utils.Views;
import com.todolist.model.Priority;
import com.todolist.model.Todo;
import com.todolist.model.api.TodoDaoException;
import com.todolist.model.api.TodoRepository;
import com.todolist.model.api.TodoRepositoryImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Servlet that controls todo update.
 *
 */

@WebServlet(name = "UpdateTodoServlet", urlPatterns = {"/todos/update", "/todos/update.do"})
public class UpdateTodoServlet extends HttpServlet {

    private TodoRepository todoService;


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        todoService = TodoRepositoryImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String id = request.getParameter("todoId");
        try {
            long todoId = Long.parseLong(id);
            Todo todo = todoService.getTodoById(todoId);
            request.setAttribute("todo", todo);
            request.getRequestDispatcher(Views.UPDATE_TODO_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", id + " - no such todo");
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        } catch (TodoDaoException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long todoId = Long.parseLong(request.getParameter("todoId"));
        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");

        try {
            Todo todo = todoService.getTodoById(todoId);
            todo.setTitle(title);




            todo.setDueDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dueDate));
            todo.setDone(Boolean.valueOf(status));
            todo.setPriority(Priority.valueOf(priority));
            todoService.update(todo);
            request.getRequestDispatcher("/todos").forward(request, response);
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
