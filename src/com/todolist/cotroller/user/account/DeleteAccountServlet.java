package com.todolist.cotroller.user.account;

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
 */

@WebServlet(name = "DeleteAccountServlet", urlPatterns = "/user/account/delete.do")
public class DeleteAccountServlet extends HttpServlet {

    private UserRepository userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        userService = UserRepositoryImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);
        try {
            userService.remove(user);
        }catch (TodoDaoException e){
            request.getRequestDispatcher(Views.ERROR_PAGE).forward(request, response);
            session.invalidate();
            return;
        }
        session.invalidate();
        request.getRequestDispatcher("/index").forward(request, response);
    }

}
