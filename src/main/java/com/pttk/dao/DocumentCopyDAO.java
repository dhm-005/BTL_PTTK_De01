
package com.pttk.dao;

import com.pttk.model.document.Document1;
import com.pttk.model.document.DocumentCopy1;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DocumentCopyDAO extends DAO {

    public DocumentCopyDAO() {
    }
    
    public DocumentCopy1 getCopyByBarcode(String code){
        String sql = "SELECT dc.*, d.id as docId, d.name "
                + "FROM tblDocumentCopy1 dc "
                + "LEFT JOIN tblDocument1 d ON dc.tblDocument1ID = d.id "
                + "WHERE dc.barcode = ? "
                + "Limit 1";
        
        DocumentCopy1 copy = null;
        try(PreparedStatement statement = con.prepareStatement(sql)){
            statement.setString(1, code);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    copy = new DocumentCopy1();
                    
                    copy.setId(rs.getLong("id"));
                    copy.setBarCode(code);
                    copy.setStatus(
                        DocumentCopy1.Status.valueOf(rs.getString("status"))
                    );
                    copy.setPhysicalCondition(
                        DocumentCopy1.PhysicalCondition.valueOf(rs.getString("physicalCondition"))
                    );
                    
                    Long docId = rs.getObject("docId", Long.class);
                    if (docId != null) {
                       Document1 doc = new Document1();
                       doc.setId(docId);
                       doc.setName(rs.getString("name"));
                       copy.setDocument(doc);
                    }
                    System.out.println("Tìm thấy bản sao: "+ copy);
                }
            }
        }  catch (SQLException ex) {
            throw new IllegalStateException("Failed to search reader by card barcode.", ex);
        }
        return copy;
    }

    
}
