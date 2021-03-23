/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import loiqd.utils.DBHelper;

/**
 *
 * @author GF65
 */
public class AccountDAO {
    
    private String userName = null;
    private boolean role;

    

    public boolean isRole() {
        return role;
    }
    
    public boolean checkLogin(String userID, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try{
            con = DBHelper.makeConnection();
            
            if(con != null) {
                String sql = "SELECT USERNAME, ROLE "
                        + "FROM ACCOUNT "
                        + "WHERE USERID = ? AND PASSWORD = ?";
                pre = con.prepareStatement(sql);
                pre.setString(1, userID);
                pre.setString(2, password);
                
                rs = pre.executeQuery();
                if(rs.next()) {
                    userName = rs.getNString("USERNAME");
                    role = rs.getBoolean("ROLE");
                    return true;
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pre != null) {
                pre.close();
            }
            if(con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public void create(String userID, String password, String userName, boolean role, String favorCate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        try{
            con = DBHelper.makeConnection();
            
            if(con != null) {
                String sql = "INSERT INTO ACCOUNT "
                        + "VALUES(?,?,?,?,?)";
                pre = con.prepareStatement(sql);
                
                pre.setString(1, userID);
                pre.setString(2, password);
                pre.setString(3, userName);
                pre.setBoolean(4, role);
                pre.setString(5, favorCate);
                
                pre.executeUpdate();
                
            }
        } finally {
            
            if(pre != null) {
                pre.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
    
    public boolean isNewAcc(String userID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try{
            con = DBHelper.makeConnection();
            
            if(con != null) {
                String sql = "SELECT USERID "
                        + "FROM ACCOUNT "
                        + "WHERE USERID = ?";
                pre = con.prepareStatement(sql);
                
                pre.setString(1, userID);
                
                rs = pre.executeQuery();
                if(rs.next()) {
                    return false;
                }
                
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pre != null) {
                pre.close();
            }
            if(con != null) {
                con.close();
            }
        }
        return true;
    }

    public String getUserName() {
        return userName;
    }
    
    public String getUserName(String userID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try{
            con = DBHelper.makeConnection();
            
            if(con != null) {
                String sql = "SELECT USERNAME "
                        + "FROM ACCOUNT "
                        + "WHERE USERID = ?";
                pre = con.prepareStatement(sql);
                
                pre.setString(1, userID);
                
                rs = pre.executeQuery();
                if(rs.next()) {
                    return rs.getNString("USERNAME");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pre != null) {
                pre.close();
            }
            if(con != null) {
                con.close();
            }
        }
        return "";
    }
    
    public String getFavorCateByUserID(String userID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try{
            con = DBHelper.makeConnection();
            
            if(con != null) {
                String sql = "SELECT FAVORCATE "
                        + "FROM ACCOUNT "
                        + "WHERE USERID = ?";
                pre = con.prepareStatement(sql);
                
                pre.setString(1, userID);
                
                rs = pre.executeQuery();
                if(rs.next()) {
                    return rs.getString("FAVORCATE");
                }
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pre != null) {
                pre.close();
            }
            if(con != null) {
                con.close();
            }
        }
        return "";
    }
}
