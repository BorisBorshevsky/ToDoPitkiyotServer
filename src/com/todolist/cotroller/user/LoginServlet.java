

package com.todolist.cotroller.user;

import com.todolist.cotroller.utils.Views;
import com.todolist.model.User;
import com.todolist.model.api.TodoDaoException;
import com.todolist.model.api.UserRepository;
import com.todolist.model.api.UserRepositoryImpl;
import com.todolist.model.utils.TodoListUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Servlet that controls the login process.
 * <p>
 * Get requests to "/login" redirects to login page.
 * Post requests to "/login.do" processes user login.
 */

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/login.do"})
public class LoginServlet extends HttpServlet {

    private UserRepository userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        userService = UserRepositoryImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("loginTabStyle", "active");
        request.getRequestDispatcher(Views.LOGIN_PAGE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nextPage = Views.LOGIN_PAGE;

        if (isInvalid(request)) {
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        if (isInvalidCombination(email, password)) {
            request.setAttribute("error", "invalid login");
        } else {
            try {
                HttpSession session = request.getSession();//create session
                User user = userService.getUserByEmail(email);
                session.setAttribute(TodoListUtils.SESSION_USER, user);
                nextPage = "/todos";
            } catch (TodoDaoException e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
            }
        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }

    private boolean isInvalidCombination(String email, String password) {
        try {
            return !userService.login(email, password);
        } catch (TodoDaoException e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean isInvalid(HttpServletRequest request) {
        return request.getAttribute("error") != null;
    }

}
