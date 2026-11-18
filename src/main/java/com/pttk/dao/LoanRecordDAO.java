package com.pttk.dao;

import com.pttk.model.borrowreturn.LoanDetail1;
import com.pttk.model.borrowreturn.LoanRecord1;
import com.pttk.model.user.Librarian1;
import com.pttk.model.user.Reader1;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LoanRecordDAO extends DAO {

    public LoanRecordDAO() {
    }

    public boolean createLoanRecord(LoanRecord1 record) throws SQLException {
        String sql1 = "INSERT INTO tblLoanRecord1(loanDate, status, readerID, librarianID) "
                + "value (?, ?, ?, ?)";
        String sql2 = "insert into tblLoanDetail1"
                + "(dueDate, renewalCount, status, tblLoanRecord1ID, tblDocumentCopy1ID) "
                + "value (?, ?, ?, ?, ?)";
        String sql3 = "UPDATE tblDocumentCopy1 SET status = 'LOANED' "
                + "WHERE id = ? AND status IN ('AVAILABLE', 'RESERVED')";

        Reader1 reader = record.getReader1();
        if (reader == null || reader.getId() == null) {
            throw new IllegalArgumentException("Chưa có thông tin bạn đọc hợp lệ.");
        }
        Librarian1 librarian = record.getLibrarian1();
        if (librarian == null || librarian.getId() == null) {
            throw new IllegalArgumentException("Chưa có thông tin thủ thư hợp lệ.");
        }
        if (record.getDetails() == null || record.getDetails().isEmpty()) {
            throw new IllegalArgumentException("Phiếu mượn chưa có tài liệu.");
        }
        for (LoanDetail1 detail : record.getDetails()) {
            if (detail.getCopy() == null || detail.getCopy().getId() == null
                    || detail.getDueDate() == null || detail.getStatus() == null) {
                throw new IllegalArgumentException("Thông tin bản sao hoặc hạn trả không hợp lệ.");
            }
        }

        try {
            con.setAutoCommit(false);

            // Thêm LoanRecord
            long loanRecordId;

            try (PreparedStatement statement = con.prepareStatement(
                    sql1, Statement.RETURN_GENERATED_KEYS)) {

                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.setString(2, record.getStatus().toString());
                statement.setLong(3, reader.getId());
                statement.setLong(4, librarian.getId());

                if (statement.executeUpdate() == 0) {
                    throw new SQLException("Không tạo được phiếu mượn.");
                }

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Không lấy được mã phiếu mượn vừa tạo.");
                    }
                    loanRecordId = rs.getLong(1);
                }
            }
            record.setId(loanRecordId);

            try (PreparedStatement detailStatement = con.prepareStatement(sql2);
                    PreparedStatement copyStatement = con.prepareStatement(sql3)) {
                for (LoanDetail1 detail : record.getDetails()) {
                    copyStatement.setLong(1, detail.getCopy().getId());
                    if (copyStatement.executeUpdate() != 1) {
                        throw new SQLException("Bản sao " + detail.getCopy().getBarCode()
                                + " không còn ở trạng thái có thể cho mượn.");
                    }

                    detailStatement.setDate(1, Date.valueOf(detail.getDueDate()));
                    detailStatement.setInt(2, detail.getRenewalCount());
                    detailStatement.setString(3, detail.getStatus().toString());
                    detailStatement.setLong(4, loanRecordId);
                    detailStatement.setLong(5, detail.getCopy().getId());
                    detailStatement.addBatch();
                }

                detailStatement.executeBatch();
            }

            con.commit();
            return true;

        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                ex.addSuppressed(e);
            }

            System.getLogger(LoanRecordDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
            throw ex;

        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                System.getLogger(LoanRecordDAO.class.getName())
                        .log(System.Logger.Level.ERROR, (String) null, ex);
                throw new IllegalStateException("Không thể kết thúc giao dịch tạo phiếu mượn.", ex);
            }
        }
    }
}
