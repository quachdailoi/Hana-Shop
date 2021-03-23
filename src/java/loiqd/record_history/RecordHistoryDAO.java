/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.record_history;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import loiqd.utils.DBHelper;

/**
 *
 * @author GF65
 */
public class RecordHistoryDAO {

    public void record(String userID, String userName, String description) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {
                String sql = "INSERT INTO RECORDHISTORY "
                        + "VALUES(?, ?, ?, GETDATE())";
                
                pre = con.prepareStatement(sql);
                
                pre.setString(1, userID);
                pre.setNString(2, userName);
                pre.setNString(3, description);
                
                pre.executeUpdate();
  
            }
        } finally {

            if (pre != null) {
                pre.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
