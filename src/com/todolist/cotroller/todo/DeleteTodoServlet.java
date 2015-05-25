
package com.todolist.cotroller.todo;

import com.todolist.cotroller.utils.Views;
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


/**
 * Servlet that controls todo deletion.
 *
 */

@WebServlet(name = "DeleteTodoServlet", urlPatterns = "/todos/delete.do")
public class DeleteTodoServlet extends HttpServlet {

    private TodoRepository todoService;


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        todoService = TodoRepositoryImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String id = request.getParameter("todoId");
        try {
            long todoId = Long.parseLong(id);
            Todo todo = todoService.getTodoById(todoId);
            if (todo != null) {
                todoService.remove(todo);
                request.getRequestDispatcher("/todos").forward(request, response);
            } else {
                request.setAttribute("error", id + " - no such todo");
                request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", id + " - no such todo");
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        } catch (TodoDaoException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        }
    }



}
