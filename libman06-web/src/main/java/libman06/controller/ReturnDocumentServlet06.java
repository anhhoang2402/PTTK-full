package libman06.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import libman06.dao.LoanDetailDAO06;
import libman06.model.LoanDetail06;
import libman06.model.ReaderCard06;
import libman06.model.ReturnInvoice06;
import libman06.model.Staff06;

@WebServlet("/staff/return-document")
public class ReturnDocumentServlet06 extends HttpServlet {
    private static final String VIEW = "/WEB-INF/views/ReturnDocumentView06.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ReaderCard06 card = (ReaderCard06) session.getAttribute("card");
        if (card == null) {
            response.sendRedirect(request.getContextPath() + "/staff/search-card");
            return;
        }

        ArrayList<LoanDetail06> listLoanDetail;
        try (LoanDetailDAO06 dao = new LoanDetailDAO06()) {
            listLoanDetail = dao.getBorrowingLoanDetails(card);
        }
        if (listLoanDetail.isEmpty()) {
            clearReturn(session);
            request.setAttribute("error", "Reader " + card.getReader().getFullName() + " (" + card.getCardCode()
                    + ") is not borrowing any document.");
            request.getRequestDispatcher(SearchReaderCardServlet06.VIEW).forward(request, response);
            return;
        }
        for (LoanDetail06 detail : listLoanDetail) {
            detail.calculateLateFine();
        }
        session.setAttribute("listLoanDetail", listLoanDetail);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Staff06 staff = (Staff06) session.getAttribute("staff");
        ReaderCard06 card = (ReaderCard06) session.getAttribute("card");
        ArrayList<LoanDetail06> listLoanDetail = (ArrayList<LoanDetail06>) session.getAttribute("listLoanDetail");
        if (card == null || listLoanDetail == null) {
            response.sendRedirect(request.getContextPath() + "/staff/search-card");
            return;
        }

        Map<Integer, Float> selection = new HashMap<>();
        String[] ids = request.getParameterValues("selectedId");
        if (ids != null) {
            for (String id : ids) {
                String fine = request.getParameter("damageFine_" + id);
                try {
                    float damage = (fine == null || fine.isBlank()) ? 0 : Float.parseFloat(fine.trim());
                    if (damage < 0) {
                        throw new NumberFormatException();
                    }
                    selection.put(Integer.valueOf(id), damage);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "The damage fine must be a positive number.");
                    request.getRequestDispatcher(VIEW).forward(request, response);
                    return;
                }
            }
        }
        if (selection.isEmpty()) {
            request.setAttribute("error", "Please tick at least one returned document.");
            request.getRequestDispatcher(VIEW).forward(request, response);
            return;
        }

        ArrayList<LoanDetail06> returned = new ArrayList<>();
        for (LoanDetail06 detail : listLoanDetail) {
            Float damage = selection.get(detail.getId());
            if (damage != null) {
                detail.setReturnDate(LocalDate.now());
                detail.setDamageFine(damage);
                detail.calculateLateFine();
                returned.add(detail);
            }
        }
        ReturnInvoice06 invoice = new ReturnInvoice06(staff, card, returned);
        invoice.calculateTotalFine();

        session.setAttribute("selection", selection);
        session.setAttribute("invoice", invoice);
        response.sendRedirect(request.getContextPath() + "/staff/return-invoice");
    }

    static void clearReturn(HttpSession session) {
        session.removeAttribute("card");
        session.removeAttribute("listLoanDetail");
        session.removeAttribute("selection");
        session.removeAttribute("invoice");
    }
}
