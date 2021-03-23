/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.order_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.NamingException;
import loiqd.cart.Cart;
import loiqd.food.FoodDAO;
import loiqd.food.FoodDTO;
import loiqd.utils.DBHelper;

/**
 *
 * @author GF65
 */
public class OrderFoodDAO {

    public void checkOut(String orderID, Cart cart, HashMap<String, FoodDTO> cartDetails) throws SQLException, NamingException {
        HashMap<String, Integer> items = cart.getItems();

        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO ORDERFOOD "
                        + "VALUES(?, ?, ?, ?)";
                
                String sqlForDecreaseQuan = "Update FOOD "
                        + "SET QUANTITY = ((SELECT QUANTITY FROM FOOD WHERE FOOD_ID = ?) - ?) "
                        + "WHERE FOOD_ID = ?";
                
                
                for (String foodId : items.keySet()) {
                    pre = con.prepareStatement(sql);
                    int quantity = items.get(foodId);
                    pre.setString(1, orderID);
                    pre.setString(2, foodId);
                    pre.setInt(3, quantity);
                    pre.setDouble(4, cartDetails.get(foodId).getPrice());
                    
                    pre.executeUpdate();
                    
                    pre = con.prepareCall(sqlForDecreaseQuan);
                    pre.setString(1, foodId);
                    pre.setInt(2, quantity);
                    pre.setString(3, foodId);
                    pre.executeUpdate();
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
    }
    
    public ArrayList<OrderFoodDTO> getFoodOfOrder(String orderId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        
        ArrayList<OrderFoodDTO> listFood = new ArrayList<>();
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT FOODID, QUANTITY, PRICE "
                        + "FROM ORDERFOOD "
                        + "WHERE ORDERID = ?";
                
                pre = con.prepareStatement(sql);
                
                pre.setString(1, orderId);
                
                rs = pre.executeQuery();
                
                while(rs.next()) {
                    String foodId = rs.getString("FOODID");
                    int quantity = rs.getInt("QUANTITY");
                    double price = rs.getDouble("PRICE");
                    OrderFoodDTO dto = new OrderFoodDTO(orderId, foodId, quantity, price);
                    listFood.add(dto);
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
        return listFood;
    }
    
    
    
    
}
