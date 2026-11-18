
package com.pttk.dao;

import com.pttk.model.borrowreturn.LoanDetail1;
import com.pttk.model.document.Document1;
import com.pttk.model.document.DocumentCopy1;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoanDetailDAO extends DAO{

    public LoanDetailDAO() {
    }
    
    
    public List<LoanDetail1> getUnfinishedLoan(Long readerId) throws SQLException {
        String sql = "SELECT ld.id AS detailId, ld.dueDate, ld.renewalCount, ld.status AS detailStatus, "
                + "dc.id AS copyId, dc.barcode, dc.status AS copyStatus, "
                + "d.id AS documentId, d.name AS documentName "
                + "FROM tblLoanDetail1 ld "
                + "JOIN tblLoanRecord1 lr ON lr.id = ld.tblLoanRecord1ID "
                + "JOIN tblDocumentCopy1 dc ON dc.id = ld.tblDocumentCopy1ID "
                + "LEFT JOIN tblDocument1 d ON d.id = dc.tblDocument1ID "
                + "WHERE lr.readerID = ? AND lr.status = 'ACTIVE' "
                + "AND ld.status IN ('BORROWING', 'OVERDUE') "
                + "ORDER BY ld.dueDate, ld.id";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, readerId);

            List<LoanDetail1> details = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    LoanDetail1 detail = new LoanDetail1();
                    detail.setId(rs.getLong("detailId"));
                    detail.setDueDate(rs.getDate("dueDate").toLocalDate());
                    detail.setRenewalCount(rs.getInt("renewalCount"));
                    detail.setStatus(LoanDetail1.Status.valueOf(rs.getString("detailStatus")));

                    DocumentCopy1 copy = new DocumentCopy1();
                    copy.setId(rs.getLong("copyId"));
                    copy.setBarCode(rs.getString("barcode"));
                    copy.setStatus(DocumentCopy1.Status.valueOf(rs.getString("copyStatus")));

                    Long documentId = rs.getObject("documentId", Long.class);
                    if (documentId != null) {
                        Document1 document = new Document1();
                        document.setId(documentId);
                        document.setName(rs.getString("documentName"));
                        copy.setDocument(document);
                    }

                    detail.setCopy(copy);
                    details.add(detail);
                }
            }
            return details;
        }
    }
}
