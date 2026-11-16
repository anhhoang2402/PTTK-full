package libman06.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import libman06.model.LoanDetail06;
import libman06.model.ReturnInvoice06;

public class ReturnInvoiceDAO06 extends DAO06 {

    public ReturnInvoiceDAO06() {
        super();
    }

    public boolean addReturnInvoice(ReturnInvoice06 invoice) {
        try {
            con.setAutoCommit(false);

            String sqlInvoice = "INSERT INTO return_invoice (return_date, total_fine, note, reader_card_id, staff_id) "
                    + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlInvoice, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(invoice.getReturnDate()));
                ps.setFloat(2, invoice.getTotalFine());
                ps.setString(3, invoice.getNote());
                ps.setInt(4, invoice.getReaderCard().getId());
                ps.setInt(5, invoice.getStaff().getId());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    invoice.setId(keys.getInt(1));
                }
            }

            String sqlDetail = "UPDATE loan_detail SET return_date = ?, late_fine = ?, damage_fine = ?, status = ?, "
                    + "note = ?, return_invoice_id = ? WHERE id = ? AND status = ?";
            String sqlDocument = "UPDATE document SET quantity = quantity + 1 WHERE id = ?";
            try (PreparedStatement psDetail = con.prepareStatement(sqlDetail);
                 PreparedStatement psDocument = con.prepareStatement(sqlDocument)) {
                for (LoanDetail06 detail : invoice.getListLoanDetail()) {
                    psDetail.setDate(1, Date.valueOf(detail.getReturnDate()));
                    psDetail.setFloat(2, detail.getLateFine());
                    psDetail.setFloat(3, detail.getDamageFine());
                    psDetail.setString(4, LoanDetail06.STATUS_RETURNED);
                    psDetail.setString(5, detail.getNote());
                    psDetail.setInt(6, invoice.getId());
                    psDetail.setInt(7, detail.getId());
                    psDetail.setString(8, LoanDetail06.STATUS_BORROWING);
                    if (psDetail.executeUpdate() != 1) {
                        con.rollback();
                        invoice.setId(0);
                        return false;
                    }
                    psDocument.setInt(1, detail.getDocument().getId());
                    psDocument.executeUpdate();
                }
            }

            con.commit();
            for (LoanDetail06 detail : invoice.getListLoanDetail()) {
                detail.setStatus(LoanDetail06.STATUS_RETURNED);
            }
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ignored) {
            }
            throw new IllegalStateException("Cannot save the return invoice: " + e.getMessage(), e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }
}
