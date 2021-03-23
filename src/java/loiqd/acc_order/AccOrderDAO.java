/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.acc_order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import loiqd.utils.DBHelper;

/**
 *
 * @author GF65
 */
public class AccOrderDAO implements Serializable {

    public String createOrder(String userId, double amount, String address, String email, String method, String receiverName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO ACCORDER(ORDERID, USERID, AMOUNT, ORDERDATE, ADDRESS, EMAIL, METHOD, STATUS, RECEIVERNAME) "
                        + "VALUES(?, ?, ?, GETDATE(),?, ?, ?, 0, ?)";

                //create Order ID
                String orderID = "OID";

                String curTimeMil = String.valueOf(System.currentTimeMillis());

                String countOrder = String.valueOf(countOrder());

                orderID += ("_" + curTimeMil);
                orderID += ("_" + countOrder);

                pre = con.prepareStatement(sql);
                pre.setString(1, orderID);
                pre.setString(2, userId);
                pre.setDouble(3, amount);
                pre.setNString(4, address);
                pre.setString(5, email);
                pre.setString(6, method);
                pre.setNString(7, receiverName);

                pre.executeUpdate();

                return orderID;
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
        return "";
    }

    public int countOrder() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        int counter = 1;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(ORDERID) AS ROWS "
                        + "FROM ACCORDER";

                pre = con.prepareStatement(sql);

                rs = pre.executeQuery();

                while (rs.next()) {
                    counter++;
                }

                return counter;
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
        return counter;
    }

    public ArrayList<AccOrderDTO> getAllOrderByUID(String userId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        
        ArrayList<AccOrderDTO> listOrder = new ArrayList<>();
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT ORDERID, AMOUNT, ORDERDATE, ADDRESS, EMAIL, METHOD, STATUS, RECEIVERNAME "
                        + "FROM ACCORDER "
                        + "WHERE USERID = ?";

                pre = con.prepareStatement(sql);
                
                pre.setString(1, userId);

                rs = pre.executeQuery();

                while (rs.next()) {
                    String orderId = rs.getString("ORDERID");
                    double amount = rs.getDouble("AMOUNT");
                    Date orderDate = rs.getDate("ORDERDATE");
                    String address = rs.getNString("ADDRESS");
                    String email = rs.getString("EMAIL");
                    String method = rs.getString("METHOD");
                    String receiverName = rs.getNString("RECEIVERNAME");
                    
                    AccOrderDTO dto = new AccOrderDTO(orderId, userId, amount, orderDate, email, address, email, method, receiverName);
                    listOrder.add(dto);
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
        return listOrder;
    }
}
