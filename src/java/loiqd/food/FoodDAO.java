/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import loiqd.account.AccountDAO;
import loiqd.cart.Cart;
import loiqd.utils.DBHelper;

/**
 *
 * @author GF65
 */
public class FoodDAO {

    public FoodDTO getFoodByID(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        FoodDTO food = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "SELECT FOOD_NAME, FOOD_ID, IMAGE, DESCRIPTION, PRICE, QUANTITY, CREATE_DATE, STATUS, CATE_ID "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);
                pre.setString(1, id);

                rs = pre.executeQuery();

                if (rs.next()) {
                    String foodName = rs.getNString("FOOD_NAME");
                    String foodId = rs.getString("FOOD_ID");
                    String imgData = rs.getString("IMAGE");
                    String description = rs.getNString("DESCRIPTION");
                    double price = rs.getDouble("PRICE");
                    int quantity = rs.getInt("QUANTITY");
                    Date createDate = rs.getDate("create_date");
                    boolean status = rs.getBoolean("STATUS");
                    String cateID = rs.getString("CATE_ID");
                    food = new FoodDTO(foodId, foodName, imgData, description, price, quantity, createDate, cateID, status);
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
        return food;
    }

    public int getQuantityByID(String foodId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "SELECT QUANTITY "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);
                pre.setString(1, foodId);

                rs = pre.executeQuery();

                if (rs.next()) {

                    return rs.getInt("QUANTITY");

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
        return 0;
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("-> " + new FoodDAO().getMaxPrice());
        } catch (NamingException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FoodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getMaxPrice() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = ""
                        + "SELECT MAX(PRICE) as MAX "
                        + "FROM FOOD";

                pre = con.prepareStatement(sql);

                rs = pre.executeQuery();

                if (rs.next()) {
                    return rs.getDouble("MAX");
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
        return 0;
    }

    private int paging;

    public ArrayList<FoodDTO> searchFoods(int rowsInAPage, int page, String searchName, String cate_id, String priceRangeStr, Boolean role) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        ArrayList<FoodDTO> listFood = new ArrayList<>();

        double fromPrice = 0;
        double toPrice = 0;

        String addingSQL = "AND STATUS = ";
        if (role == null || !role) {
            addingSQL += "1";
        } else {
            addingSQL = "";
        }

        if (priceRangeStr == null || priceRangeStr.contains("none") || priceRangeStr.isEmpty()) {
            toPrice = getMaxPrice();
        } else if (!priceRangeStr.contains(",")) {
            fromPrice = 1501000;
            toPrice = getMaxPrice();
        } else {
            String[] fromTo = priceRangeStr.split(",");
            fromPrice = Double.parseDouble(fromTo[0]);
            toPrice = Double.parseDouble(fromTo[1]);
        }
        
        if (searchName == null || searchName.isEmpty()) {
            searchName = "";
        }
        if (cate_id == null || cate_id.contains("none") || cate_id.isEmpty()) {
            cate_id = "%%";
        }

        int row = 0;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sqlTolal = "SELECT COUNT(FOOD_ID) as ROWS "
                        + "FROM FOOD "
                        + "Where FOOD_NAME LIKE ? AND CATE_ID LIKE ? AND PRICE BETWEEN ? AND ? " + addingSQL;

                pre = con.prepareStatement(sqlTolal);
                pre.setNString(1, "%" + searchName + "%");
                pre.setString(2, cate_id);
                pre.setDouble(3, fromPrice);
                pre.setDouble(4, toPrice);
                rs = pre.executeQuery();

                if (rs.next()) {
                    row = rs.getInt("ROWS");
                    maxFood = row;
                }

                String sql = "SELECT * "
                        + "FROM FOOD "
                        + "Where FOOD_NAME LIKE ? AND CATE_ID LIKE ? AND PRICE BETWEEN ? AND ? " + addingSQL
                        + " Order by create_date desc "
                        + "offset ? rows fetch next ? rows only";

                int offset = rowsInAPage * (page - 1);
                int next = rowsInAPage;

                pre = con.prepareStatement(sql);
                pre.setNString(1, "%" + searchName + "%");
                pre.setString(2, cate_id);
                pre.setDouble(3, fromPrice);
                pre.setDouble(4, toPrice);
                pre.setInt(5, offset);
                pre.setInt(6, next);

                rs = pre.executeQuery();

                while (rs.next()) {
                    String foodName = rs.getNString("FOOD_NAME");
                    String foodId = rs.getString("FOOD_ID");
                    String imgData = rs.getString("IMAGE");
                    String description = rs.getNString("DESCRIPTION");
                    double price = rs.getDouble("PRICE");
                    int quantity = rs.getInt("QUANTITY");
                    Date createDate = rs.getDate("create_date");
                    boolean status = rs.getBoolean("STATUS");
                    String cateID = rs.getString("CATE_ID");
                    listFood.add(new FoodDTO(foodId, foodName, imgData, description, price, quantity, createDate, cateID, status));
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
        paging = row % rowsInAPage == 0 ? row / rowsInAPage : row / rowsInAPage + 1;
        return listFood;
    }

    private int maxFood;

    public int getMaxFood() {
        return maxFood;
    }

    public int getMaxPage(int rowsInAPage) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "SELECT COUNT(FOOD_ID) as MAXROWS "
                        + "FROM FOOD";

                pre = con.prepareStatement(sql);

                rs = pre.executeQuery();

                if (rs.next()) {
                    int row = rs.getInt("MAXROWS");
                    return row % rowsInAPage == 0 ? row / rowsInAPage : row / rowsInAPage + 1;
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
        return 0;
    }

    public int getPaging() {
        return paging;
    }

    public void deleteFoods(String[] ids) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "UPDATE FOOD "
                        + "SET STATUS = 0 "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                for (String id : ids) {
                    pre.setString(1, id);
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

    public void updateFood(FoodDTO food) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "UPDATE FOOD "
                        + "SET FOOD_NAME = ?, IMAGE = ?, DESCRIPTION = ?, PRICE = ?, QUANTITY = ?, CREATE_DATE = GETDATE(), STATUS = ?, CATE_ID = ? "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                pre.setString(1, food.getFoodName());
                pre.setString(2, food.getImgData());
                pre.setNString(3, food.getDescription());
                pre.setDouble(4, food.getPrice());
                pre.setInt(5, food.getQuantity());
                pre.setBoolean(6, food.isStatus());
                pre.setString(7, food.getCateId());
                pre.setString(8, food.getFoodId());

                pre.executeUpdate();
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

    public String getNameByID(String food_id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        String food_name = "";
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "SELECT FOOD_NAME "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                pre.setString(1, food_id);

                rs = pre.executeQuery();
                if (rs.next()) {
                    food_name = rs.getNString("FOOD_NAME");
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
        return food_name;
    }

    public void createFood(String food_id, String food_name, String data_image, String food_description, double food_price, int food_quantity, String cate_id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String sql = "INSERT INTO FOOD(FOOD_ID, FOOD_NAME, IMAGE, DESCRIPTION, PRICE, QUANTITY, CREATE_DATE, STATUS, CATE_ID) "
                        + "VALUES(?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";

                pre = con.prepareStatement(sql);

                pre.setString(1, food_id);
                pre.setNString(2, food_name);
                pre.setString(3, data_image);
                pre.setNString(4, food_description);
                pre.setDouble(5, food_price);
                pre.setInt(6, food_quantity);
                pre.setBoolean(7, true);
                pre.setString(8, cate_id);

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

    public HashMap<String, FoodDTO> getListFoodForCart(HashMap<String, Integer> itemsCart) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        HashMap<String, FoodDTO> cartDetail = new HashMap<>();

        con = DBHelper.makeConnection();
        try {
            if (con != null) {

                String sql = "SELECT FOOD_NAME, IMAGE, PRICE, QUANTITY "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                for (String foodId : itemsCart.keySet()) {
                    pre.setString(1, foodId);

                    rs = pre.executeQuery();

                    if (rs.next()) {
                        String foodName = rs.getNString("FOOD_NAME");
                        String imgData = rs.getString("IMAGE");
                        double price = rs.getDouble("PRICE");
                        int quantity = rs.getInt("QUANTITY");
                        cartDetail.put(foodId, new FoodDTO(foodName, imgData, price, quantity));
                    }
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
        return cartDetail;
    }

    public double getPriceByID(String foodId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        double price = 0;

        con = DBHelper.makeConnection();
        try {
            if (con != null) {

                String sql = "SELECT PRICE "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                rs = pre.executeQuery();

                if (rs.next()) {
                    return rs.getDouble("PRICE");
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
        return price;
    }

    public HashMap<String, Integer> checkValidCart(Cart cart) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        HashMap<String, Integer> limitQuan = new HashMap<>();

        con = DBHelper.makeConnection();
        try {
            if (con != null) {

                String sql = "SELECT QUANTITY "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                for (String food_id : cart.getItems().keySet()) {

                    pre.setString(1, food_id);

                    rs = pre.executeQuery();

                    if (rs.next()) {
                        int quanInStore = rs.getInt("QUANTITY");
                        int quanInCart = cart.getItems().get(food_id);
                        if (quanInStore < quanInCart) {
                            limitQuan.put(food_id, quanInStore);
                        }
                    }
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
        return limitQuan;
    }

    public String getDataImageByID(String foodId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        String image = "";

        con = DBHelper.makeConnection();
        try {
            if (con != null) {

                String sql = "SELECT IMAGE "
                        + "FROM FOOD "
                        + "WHERE FOOD_ID = ?";

                pre = con.prepareStatement(sql);

                pre.setString(1, foodId);

                rs = pre.executeQuery();

                if (rs.next()) {
                    return rs.getString("IMAGE");
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
        return image;
    }

    public TreeSet<String> getOrderIds(String foodId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        TreeSet<String> listOrderId = new TreeSet<>();
        try {
            con = DBHelper.makeConnection();

            String sql = "SELECT b.ORDERID "
                    + "FROM ORDERFOOD a JOIN ACCORDER b ON a.orderId = b.orderId "
                    + "WHERE a.FOODID = ? "
                    + "ORDER BY ORDERDATE DESC ";

            pre = con.prepareStatement(sql);
            pre.setString(1, foodId);
            

            rs = pre.executeQuery();
            while (rs.next()) {
                listOrderId.add(rs.getString("ORDERID"));
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
        return listOrderId;
    }

    public TreeSet<String> getFoodIdOfOrder(String orderId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        TreeSet<String> listFood = new TreeSet<>();

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT FOODID "
                        + "FROM ORDERFOOD "
                        + "WHERE ORDERID = ?";

                pre = con.prepareStatement(sql);

                pre.setString(1, orderId);

                rs = pre.executeQuery();

                while (rs.next()) {
                    String foodId = rs.getString("FOODID");

                    listFood.add(foodId);
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

    public TreeSet<FoodDTO> getSuggestFood(String userId, Cart cart) throws NamingException, SQLException {
        
        ArrayList<FoodDTO> listFoodDTO = new ArrayList<>();
        ArrayList<String> listOrder = new ArrayList<>();
        ArrayList<String> listFood = new ArrayList<>();

        for (String foodId : cart.getItems().keySet()) {
            listOrder.addAll(getOrderIds(foodId));
        }
        
        for (String orderId : listOrder) {
            listFood.addAll(getFoodIdOfOrder(orderId));
        }

        for (String foodId : listFood) {
            listFoodDTO.add(getFoodByID(foodId));
        }
        
        AccountDAO accDao = new AccountDAO();
        String favorCateId = accDao.getFavorCateByUserID(userId);
        
        TreeSet<FoodDTO> suggestList = new TreeSet<>();
        
        for (FoodDTO food : listFoodDTO) {
            if(food.getCateId().equals(favorCateId)) {
                suggestList.add(food);
            }
        }
        if(suggestList.size() < 7)
            for (int i = 0; i < listFoodDTO.size(); i++) {
                suggestList.add(listFoodDTO.get(i));
                if(suggestList.size() == 7) break;
            }
                
        return suggestList;
    }
}
