package libman06.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import libman06.model.Document06;
import libman06.model.LoanDetail06;
import libman06.model.LoanSlip06;
import libman06.model.ReaderCard06;

public class LoanDetailDAO06 extends DAO06 {

    public LoanDetailDAO06() {
        super();
    }

    public ArrayList<LoanDetail06> getBorrowingLoanDetails(ReaderCard06 card) {
        String sql = "SELECT ld.id, ld.due_date, ld.status, ld.note, ls.id AS slip_id, ls.borrow_date, "
                + "d.id AS doc_id, d.code, d.title, d.author, d.publisher, d.publish_year, d.category, "
                + "d.quantity, d.price "
                + "FROM loan_detail ld "
                + "JOIN loan_slip ls ON ls.id = ld.loan_slip_id "
                + "JOIN document d ON d.id = ld.document_id "
                + "WHERE ls.reader_card_id = ? AND ld.status = ? "
                + "ORDER BY ld.due_date, ld.id";
        ArrayList<LoanDetail06> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, card.getId());
            ps.setString(2, LoanDetail06.STATUS_BORROWING);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LoanSlip06 slip = new LoanSlip06();
                    slip.setId(rs.getInt("slip_id"));
                    slip.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                    slip.setReaderCard(card);

                    Document06 doc = new Document06();
                    doc.setId(rs.getInt("doc_id"));
                    doc.setCode(rs.getString("code"));
                    doc.setTitle(rs.getString("title"));
                    doc.setAuthor(rs.getString("author"));
                    doc.setPublisher(rs.getString("publisher"));
                    doc.setPublishYear(rs.getInt("publish_year"));
                    doc.setCategory(rs.getString("category"));
                    doc.setQuantity(rs.getInt("quantity"));
                    doc.setPrice(rs.getFloat("price"));

                    LoanDetail06 detail = new LoanDetail06();
                    detail.setId(rs.getInt("id"));
                    detail.setDueDate(rs.getDate("due_date").toLocalDate());
                    detail.setStatus(rs.getString("status"));
                    detail.setNote(rs.getString("note"));
                    detail.setLoanSlip(slip);
                    detail.setDocument(doc);
                    list.add(detail);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot load the borrowed documents: " + e.getMessage(), e);
        }
        return list;
    }
}
