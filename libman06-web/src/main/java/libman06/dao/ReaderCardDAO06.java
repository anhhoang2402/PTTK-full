package libman06.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import libman06.model.Reader06;
import libman06.model.ReaderCard06;

public class ReaderCardDAO06 extends DAO06 {

    public ReaderCardDAO06() {
        super();
    }

    public boolean addReaderCard(ReaderCard06 card) {
        Reader06 reader = card.getReader();
        try {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement("SELECT id FROM reader WHERE student_code = ?")) {
                ps.setString(1, reader.getStudentCode());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        con.rollback();
                        return false;
                    }
                }
            }

            String sqlReader = "INSERT INTO reader (full_name, date_of_birth, gender, student_code, class_name, "
                    + "address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlReader, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, reader.getFullName());
                ps.setDate(2, Date.valueOf(reader.getDateOfBirth()));
                ps.setString(3, reader.getGender());
                ps.setString(4, reader.getStudentCode());
                ps.setString(5, reader.getClassName());
                ps.setString(6, reader.getAddress());
                ps.setString(7, reader.getEmail());
                ps.setString(8, reader.getPhone());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    reader.setId(keys.getInt(1));
                }
            }

            String sqlCard = "INSERT INTO reader_card (card_type, issue_date, expiry_date, status, reader_id) "
                    + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlCard, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, card.getCardType());
                ps.setDate(2, Date.valueOf(card.getIssueDate()));
                ps.setDate(3, Date.valueOf(card.getExpiryDate()));
                ps.setString(4, card.getStatus());
                ps.setInt(5, reader.getId());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    card.setId(keys.getInt(1));
                }
            }

            card.setCardCode(String.format("RC%06d", card.getId()));
            try (PreparedStatement ps = con.prepareStatement("UPDATE reader_card SET card_code = ? WHERE id = ?")) {
                ps.setString(1, card.getCardCode());
                ps.setInt(2, card.getId());
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            rollbackQuietly();
            throw new IllegalStateException("Cannot save the reader card: " + e.getMessage(), e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    public ReaderCard06 getReaderCardByCode(String code) {
        String sql = "SELECT c.*, r.full_name, r.date_of_birth, r.gender, r.student_code, r.class_name, "
                + "r.address, r.email, r.phone FROM reader_card c JOIN reader r ON r.id = c.reader_id "
                + "WHERE c.card_code = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                ReaderCard06 card = new ReaderCard06();
                card.setId(rs.getInt("id"));
                card.setCardCode(rs.getString("card_code"));
                card.setCardType(rs.getString("card_type"));
                card.setIssueDate(rs.getDate("issue_date").toLocalDate());
                card.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
                card.setStatus(rs.getString("status"));

                Reader06 reader = new Reader06();
                reader.setId(rs.getInt("reader_id"));
                reader.setFullName(rs.getString("full_name"));
                reader.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                reader.setGender(rs.getString("gender"));
                reader.setStudentCode(rs.getString("student_code"));
                reader.setClassName(rs.getString("class_name"));
                reader.setAddress(rs.getString("address"));
                reader.setEmail(rs.getString("email"));
                reader.setPhone(rs.getString("phone"));
                card.setReader(reader);
                return card;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot search the reader card: " + e.getMessage(), e);
        }
    }

    private void rollbackQuietly() {
        try {
            con.rollback();
        } catch (SQLException ignored) {
        }
    }
}
