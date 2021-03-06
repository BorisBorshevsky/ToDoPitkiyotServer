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


@WebServlet(name = "UpdateAccountServlet", urlPatterns = "/user/account/update.do")
public class UpdateAccountServlet extends HttpServlet {

    private UserRepository userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        userService = ServiceDaoHelper.getUserRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);

        try {
            if (isAlreadyUsed(email) && isDifferent(email, user.getEmail())) {
                request.setAttribute("error", email + " is already in use.");
                request.setAttribute("user", user);
                request.getRequestDispatcher(Views.ACCOUNT_PAGE).forward(request, response);
                return;
            }
            user.setName(name);
            user.setEmail(email);
            userService.update(user);
            request.setAttribute("updateProfileSuccessMessage", "Profile updated Successfully.");
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/user/account").forward(request, response);

    }

    private boolean isDifferent(String newEmail, String currentEmail) {
        return !newEmail.equals(currentEmail);
    }

    private boolean isAlreadyUsed(String email) throws TodoDaoException {
        return userService.getUserByEmail(email) != null;
    }

}
