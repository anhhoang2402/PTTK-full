package libman06.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import libman06.dao.ReaderCardDAO06;
import libman06.model.Reader06;
import libman06.model.ReaderCard06;

@WebServlet("/register-card")
public class RegisterCardServlet06 extends HttpServlet {
    private static final String VIEW = "/WEB-INF/views/RegisterCardView06.jsp";
    private static final int CARD_YEARS = 4;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = param(request, "fullName");
        String dateOfBirth = param(request, "dateOfBirth");
        String gender = param(request, "gender");
        String studentCode = param(request, "studentCode").toUpperCase();
        String className = param(request, "className").toUpperCase();
        String address = param(request, "address");
        String email = param(request, "email");
        String phone = param(request, "phone");
        String cardType = param(request, "cardType");

        LocalDate dob = null;
        try {
            dob = LocalDate.parse(dateOfBirth);
        } catch (DateTimeParseException e) {
        }
        String all = fullName + gender + studentCode + className + address + email + phone + cardType;
        if (fullName.isEmpty() || dob == null || gender.isEmpty() || studentCode.isEmpty() || className.isEmpty()
                || cardType.isEmpty() || !email.matches("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+")
                || !phone.matches("0\\d{9}") || all.contains("<") || all.contains(">")) {
            request.setAttribute("error", "Please enter all required fields in the right format.");
            request.getRequestDispatcher(VIEW).forward(request, response);
            return;
        }

        Reader06 reader = new Reader06();
        reader.setFullName(fullName);
        reader.setDateOfBirth(dob);
        reader.setGender(gender);
        reader.setStudentCode(studentCode);
        reader.setClassName(className);
        reader.setAddress(address);
        reader.setEmail(email);
        reader.setPhone(phone);

        ReaderCard06 card = new ReaderCard06();
        card.setCardType(cardType);
        card.setIssueDate(LocalDate.now());
        card.setExpiryDate(LocalDate.now().plusYears(CARD_YEARS));
        card.setStatus(ReaderCard06.STATUS_ACTIVE);
        card.setReader(reader);

        boolean saved;
        try (ReaderCardDAO06 dao = new ReaderCardDAO06()) {
            saved = dao.addReaderCard(card);
        }
        if (!saved) {
            request.setAttribute("error", "Student " + studentCode + " already has a reader card.");
            request.getRequestDispatcher(VIEW).forward(request, response);
            return;
        }
        request.setAttribute("card", card);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    private static String param(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }
}
