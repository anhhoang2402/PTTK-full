package libman06.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import libman06.dao.ReaderCardDAO06;
import libman06.model.ReaderCard06;

@WebServlet("/staff/search-card")
public class SearchReaderCardServlet06 extends HttpServlet {
    static final String VIEW = "/WEB-INF/views/SearchReaderCardView06.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ReturnDocumentServlet06.clearReturn(request.getSession());
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("cardCode");
        code = code == null ? "" : code.trim().toUpperCase();
        if (code.isEmpty()) {
            request.setAttribute("error", "Please scan the card or enter the card code.");
            request.getRequestDispatcher(VIEW).forward(request, response);
            return;
        }

        ReaderCard06 card;
        try (ReaderCardDAO06 dao = new ReaderCardDAO06()) {
            card = dao.getReaderCardByCode(code);
        }
        if (card == null) {
            request.setAttribute("error", "Reader card " + code + " not found.");
            request.getRequestDispatcher(VIEW).forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        ReturnDocumentServlet06.clearReturn(session);
        session.setAttribute("card", card);
        response.sendRedirect(request.getContextPath() + "/staff/return-document");
    }
}
