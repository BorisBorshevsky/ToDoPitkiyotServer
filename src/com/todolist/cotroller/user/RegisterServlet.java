/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package com.todolist.cotroller.user;

import com.todolist.cotroller.utils.Views;
import com.todolist.model.User;
import com.todolist.model.api.TodoDaoException;
import com.todolist.model.api.UserRepository;
import com.todolist.model.api.UserRepositoryDao;
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
 * Servlet that controls the registration process.
 */

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register", "/register.do"})
public class RegisterServlet extends HttpServlet {

    private UserRepository userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        userService = UserRepositoryDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("registerTabStyle", "active");
        request.getRequestDispatcher(Views.REGISTER_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmationPassword = request.getParameter("confirmationPassword");

        String nextPage = Views.REGISTER_PAGE;
        checkPasswordsMatch(request, password, confirmationPassword);

        try {
            if (isAlreadyUsed(email)) {
                request.setAttribute("error", email + " Already exists.");
                request.getRequestDispatcher(nextPage).forward(request, response);
                return;
            }
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
            return;
        }

        if (isInvalid(request)) {
            request.getRequestDispatcher(nextPage).forward(request, response);
            return;
        }

        try {
            User user = new User(name, email, password);
            user = userService.create(user);
            HttpSession session = request.getSession();
            session.setAttribute(TodoListUtils.SESSION_USER, user);
            nextPage = "/todos";
            request.getRequestDispatcher(nextPage).forward(request, response);
        } catch (TodoDaoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
        }
    }

    private boolean isAlreadyUsed(String email) throws TodoDaoException {
        return userService.getUserByEmail(email) != null;

    }

    private boolean isInvalid(HttpServletRequest request) {
        return request.getAttribute("error") != null;
    }

    private void checkPasswordsMatch(HttpServletRequest request, String password, String confirmationPassword) {
        if (!confirmationPassword.equals(password)) {
            request.setAttribute("errorConfirmationPasswordMatching", "Password do not match");
            addGlobalRegistrationErrorAttribute(request);
        }
    }

    private void addGlobalRegistrationErrorAttribute(HttpServletRequest request) {
        request.setAttribute("error", "Error in Registration.");
    }

}
