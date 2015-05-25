
package com.todolist.cotroller.filter;


import com.todolist.cotroller.utils.Views;
import com.todolist.model.User;
import com.todolist.model.utils.TodoListUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//
///**
// * Filter to ensure that access to private resources is allowed only to logged users.
// *
// */
//@WebFilter(filterName = "LoginFilter", urlPatterns = {"/user/*", "/todos/*"})
//public class LoginFilter implements Filter {
//
//    public void init(FilterConfig config) throws ServletException {
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpSession session = req.getSession();
//        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);
//        if (user != null) {
//            chain.doFilter(request, response);
//        } else {
//            request.getRequestDispatcher(Views.LOGIN_PAGE).forward(request, response);
//        }
//    }
//
//
//    public void destroy() {
//    }
//
//}
