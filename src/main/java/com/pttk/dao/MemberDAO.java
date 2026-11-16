package com.pttk.dao;

import com.pttk.model.user.Librarian1;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pttk.model.user.Member1;
import com.pttk.model.user.Reader1;
import com.pttk.utils.AuthUtils;

public class MemberDAO extends DAO {

    public MemberDAO() {
        super();
    }

    public Member1 auth(String username, String password) throws SQLException {
        if (username == null || password == null) {
            return null;
        }

        // Lấy thêm cột role để biết tạo đối tượng con nào
        String sql = "SELECT id, fullName, email, phone, profilePicture, role, hashPassword, readerCode "
                + "FROM tblMember1 "
                + "WHERE username = ? AND status = 'ACTIVE' "
                + "LIMIT 1";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, username);
        
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String hashedPw = rs.getString("hashPassword");
                    boolean valid = AuthUtils.verifyPassword(password, hashedPw);
                    if(!valid) return null;
                    
                    String role = rs.getString("role");
                    Member1 member = null;

                    // Dựa vào role trong DB để khởi tạo đúng lớp con của abstract Member1
                    if ("READER".equalsIgnoreCase(role)) {
                        Reader1 reader = new Reader1();
                        reader.setReaderCode(rs.getString("readerCode"));
                        member = reader;
                    } 
                    else if ("LIBRARIAN".equalsIgnoreCase(role)) {
                       Librarian1 lib = new Librarian1();
                       member = lib;
                    }

                    if (member != null) {
                        member.setId(rs.getLong("id"));
                        member.setUsername(username);
                        member.setFullname(rs.getString("fullName"));
                        member.setEmail(rs.getString("email"));
                        member.setPhone(rs.getString("phone"));
                        member.setProfilePicture(rs.getString("profilePicture"));
                    }
                    
                    return member;
                }
            }
        }
        return null; // Sai tài khoản/mật khẩu hoặc tài khoản bị khóa
    }
}