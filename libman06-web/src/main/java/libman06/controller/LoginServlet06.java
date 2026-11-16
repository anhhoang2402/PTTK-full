package libman06.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import libman06.dao.StaffDAO06;
import libman06.model.Staff06;

@WebServlet({"/login", "/logout"})
public class LoginServlet06 extends HttpServlet {
    private static final String VIEW = "/WEB-INF/views/LoginView06.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("/logout".equals(request.getServletPath())) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Staff06 staff = new Staff06();
        staff.setUsername(request.getParameter("username"));
        staff.setPassword(request.getParameter("password"));

        boolean ok;
        try (StaffDAO06 dao = new StaffDAO06()) {
            ok = dao.checkLogin(staff);
        }
        if (!ok) {
            request.setAttribute("error", "Wrong username or password.");
            request.getRequestDispatcher(VIEW).forward(request, response);
            return;
        }
        request.getSession().setAttribute("staff", staff);
        response.sendRedirect(request.getContextPath() + "/staff/home");
    }
}
