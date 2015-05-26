
package com.todolist.cotroller.user.account;

import com.todolist.cotroller.utils.Views;
import com.todolist.model.User;
import com.todolist.model.api.ServiceDaoHelper;
import com.todolist.model.api.TodoDaoException;
import com.todolist.model.api.UserRepository;
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
 */

@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/user/account/password.do")
public class ChangePasswordServlet extends HttpServlet {

    private UserRepository userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        userService = ServiceDaoHelper.getUserRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmationPassword = request.getParameter("confirmationPassword");


        String nextPage = Views.ACCOUNT_PAGE;


        if (isInvalid(request)) {
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);
        checkPasswordsMatch(request, newPassword, confirmationPassword);

        if (!confirmationPassword.equals(newPassword)) {
            request.setAttribute("errorConfirmationPassword", "Passwords do not match.");
            addGlobalChangePasswordErrorAttribute(request);
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        if (!currentPassword.equals(user.getPassword())) {
            request.setAttribute("errorCurrentPassword", "Current Password Incorrect.");
            addGlobalChangePasswordErrorAttribute(request);
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        try {
            user.setPassword(newPassword);
            userService.update(user);
            session.setAttribute(TodoListUtils.SESSION_USER, user);
            request.setAttribute("updatePasswordSuccessMessage", "Password changes Successfully");
            nextPage = "/user/account";
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher(nextPage).forward(request, response);

    }

    private void addGlobalChangePasswordErrorAttribute(HttpServletRequest request) {
        request.setAttribute("error", "error in changing password");
    }

    private void checkPasswordsMatch(HttpServletRequest request, String password, String confirmationPassword) {
        if (!confirmationPassword.equals(password)) {
            request.setAttribute("errorConfirmationPasswordMatching", "Password do not match");
            addGlobalChangePasswordErrorAttribute(request);
        }
    }

    private boolean isInvalid(HttpServletRequest request) {
        return request.getAttribute("error") != null;
    }

}
