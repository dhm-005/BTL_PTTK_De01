package com.pttk.dao;

import com.pttk.model.user.Member1;
import com.pttk.model.user.Reader1;
import com.pttk.model.user.reader.LoanPolicy1;
import com.pttk.model.user.reader.ReaderGroup1;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReaderDAO extends DAO {

    public ReaderDAO() {
    }

    public Reader1 getReaderByCardBarcode(String code){
        String sql = "SELECT m.*, rg.id as rgID, rg.name as rgName, "
                + "lp.id as lpID, lp.maxBorrowed, lp.loanPeriod "
                + "FROM tblMember1 m "
                + "JOIN tblLibraryCard1 lc ON m.id = lc.readerID AND lc.barcode = ? "
                + "LEFT JOIN tblReaderGroup1 rg ON m.tblReaderGroup1ID = rg.id "
                + "LEFT JOIN tblLoanPolicy1 lp ON lp.id = rg.tblLoanPolicy1ID "
                + "WHERE m.role = 'READER' "
                + "Limit 1";
        
        Reader1 reader = null;
        try(PreparedStatement statement = con.prepareStatement(sql)){
            statement.setString(1, code);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    reader = new Reader1();
                    
                    reader.setId(rs.getLong("id"));
                    reader.setFullname(rs.getString("fullName"));
                    reader.setUsername(rs.getString("username"));
                    reader.setAccountStatus(
                        Member1.AccountStatus.valueOf(rs.getString("accountStatus"))
                    );
                    reader.setBirthday(
                        rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null
                    );
                    reader.setProfilePicture(rs.getString("profilePicture"));
                    
                    Long groupId = rs.getObject("rgID", Long.class);
                    if (groupId != null) {
                        ReaderGroup1 group = new ReaderGroup1();
                        group.setId(groupId);
                        group.setName(rs.getString("rgName"));

                        Long policyId = rs.getObject("lpID", Long.class);
                        if (policyId != null) {
                            LoanPolicy1 policy = new LoanPolicy1();
                            policy.setId(policyId);
                            policy.setMaxBorrowed(rs.getInt("maxBorrowed"));
                            policy.setLoanPeriod(rs.getInt("loanPeriod"));
                            group.setPolicy(policy);
                        }
                        reader.setGroup(group);
                    }
                    System.out.println("Tìm thấy bạn đọc: "+ reader);
                }
            }
        }  catch (SQLException ex) {
            throw new IllegalStateException("Failed to search reader by card barcode.", ex);
        }
        return reader;
    }
}
