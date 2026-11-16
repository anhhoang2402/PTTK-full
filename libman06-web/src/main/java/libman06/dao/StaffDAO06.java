package libman06.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import libman06.model.Staff06;

public class StaffDAO06 extends DAO06 {

    public StaffDAO06() {
        super();
    }

    public boolean checkLogin(Staff06 staff) {
        String sql = "SELECT id, full_name, position, phone FROM staff WHERE username = ? AND password = SHA2(?, 256)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, staff.getUsername());
            ps.setString(2, staff.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }
                staff.setId(rs.getInt("id"));
                staff.setFullName(rs.getString("full_name"));
                staff.setPosition(rs.getString("position"));
                staff.setPhone(rs.getString("phone"));
                staff.setPassword(null);
                return true;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot check the login: " + e.getMessage(), e);
        }
    }
}
