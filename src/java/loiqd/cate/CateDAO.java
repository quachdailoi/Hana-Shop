/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.cate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import loiqd.utils.DBHelper;

/**
 *
 * @author GF65
 */
public class CateDAO {

    public ArrayList<CateDTO> getCates() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        
        ArrayList<CateDTO> listCate = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();

            if (con != null) {
                String sql = "SELECT CATE_ID, CATE_NAME "
                        + "FROM CATEGORY";
                
                pre = con.prepareStatement(sql);

                rs = pre.executeQuery();
                
                while(rs.next()) {
                    String cateName = rs.getNString("CATE_NAME");
                    String cateId = rs.getString("CATE_ID");
                    listCate.add(new CateDTO(cateId, cateName));
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCate;
    }
}
