/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import loiqd.food.FoodDAO;

/**
 *
 * @author GF65
 */
public class CreateFoodServlet extends HttpServlet {
    final String HOME_PAGE = "SearchServlet";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String data_image = request.getParameter("p_data_image");
        String cate_id = request.getParameter("p_cate_id");
        String food_name = request.getParameter("p_food_name");
        String food_priceStr = request.getParameter("p_food_price");
        String food_quantityStr = request.getParameter("p_food_quantity");
        String food_description = request.getParameter("p_food_description");
        double food_price;
        try {
            food_price = Double.parseDouble(food_priceStr);
        } catch (NumberFormatException ex) {
            food_price = 0;
        }
        
        int food_quantity;
        try {
            food_quantity = Integer.parseInt(food_quantityStr);
        } catch (NumberFormatException ex) {
            food_quantity = 0;
        }
        
        
        FoodDAO dao = new FoodDAO();
        try {
            
            dao.searchFoods(8, 1, "", "", "", new Boolean(true));
            int numRowInt = dao.getMaxFood();
            String numRowStr = String.valueOf(numRowInt);
            //create food_id
            String food_id = numRowStr;
            for (int i = 0; i < 4 - numRowStr.length(); i++) {
                food_id = "0" + food_id;
            }
            food_id = "food_" + food_id;
            
            dao.createFood(food_id, food_name, data_image, food_description, food_price, food_quantity, cate_id);
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }
        
        request.getRequestDispatcher(HOME_PAGE).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
