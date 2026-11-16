package libman06.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import libman06.dao.ReturnInvoiceDAO06;
import libman06.model.ReturnInvoice06;

@WebServlet("/staff/return-invoice")
public class ReturnInvoiceServlet06 extends HttpServlet {
    private static final String VIEW = "/WEB-INF/views/ReturnInvoiceView06.jsp";
    private static final String SLIP_VIEW = "/WEB-INF/views/PaymentSlipView06.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("invoice") == null) {
            response.sendRedirect(request.getContextPath() + "/staff/search-card");
            return;
        }
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ReturnInvoice06 invoice = (ReturnInvoice06) session.getAttribute("invoice");
        if (invoice == null) {
            response.sendRedirect(request.getContextPath() + "/staff/search-card");
            return;
        }
        if (!"confirm".equals(request.getParameter("action"))) {
            session.removeAttribute("invoice");
            response.sendRedirect(request.getContextPath() + "/staff/return-document");
            return;
        }

        boolean saved;
        try (ReturnInvoiceDAO06 dao = new ReturnInvoiceDAO06()) {
            saved = dao.addReturnInvoice(invoice);
        }
        if (!saved) {
            session.removeAttribute("invoice");
            session.removeAttribute("selection");
            session.setAttribute("flash", "Some of these documents were already returned. Please check the list again.");
            response.sendRedirect(request.getContextPath() + "/staff/return-document");
            return;
        }
        ReturnDocumentServlet06.clearReturn(session);
        request.setAttribute("invoice", invoice);
        request.getRequestDispatcher(SLIP_VIEW).forward(request, response);
    }
}
