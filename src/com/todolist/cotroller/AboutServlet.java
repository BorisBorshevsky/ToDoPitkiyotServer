
package com.todolist.cotroller;

import com.todolist.cotroller.utils.Views;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet that controls the "about" page.
 *
 */

@WebServlet(name = "AboutServlet", urlPatterns = "/about")
public class AboutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("aboutTabStyle", "active");
        request.getRequestDispatcher(Views.ABOUT_PAGE).forward(request, response);
    }

}
