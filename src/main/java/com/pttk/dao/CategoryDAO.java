
package com.pttk.dao;

import com.pttk.model.document.Category1;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoryDAO extends DAO{

    public CategoryDAO() {
    }
    
    public Category1 findById(Long id){
        String sql = "select top 1 from tblCategory1 t where t.id = ?";
        Category1 cat = null;
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if(rs.next()){
                    cat = new Category1();
                    cat.setId(rs.getLong("id"));
                    cat.setName(rs.getString("name"));
                    cat.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException ex) {
            System.getLogger(CategoryDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return cat;
    }
    
    
}
