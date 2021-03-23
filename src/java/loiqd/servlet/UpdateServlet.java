/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.food.FoodDAO;
import loiqd.food.FoodDTO;
import loiqd.record_history.RecordHistoryDAO;

/**
 *
 * @author GF65
 */
public class UpdateServlet extends HttpServlet {
    final String SEARCH = "SearchServlet";
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
        //param of new food
        String data_image = request.getParameter("p_data_image");
        String food_id = request.getParameter("p_food_id");
        String food_name = request.getParameter("p_food_name");
        String priceStr = request.getParameter("p_food_price");
        String quantityStr = request.getParameter("p_food_quantity");
        String food_description = request.getParameter("p_food_description");
        String food_status = request.getParameter("p_food_status");
        String cate_id = request.getParameter("p_cate_id");
        
        //param of old food
        String old_dataImg = request.getParameter("p_old_data_image");
        String old_food_name = request.getParameter("p_old_food_name");
        String old_priceStr = request.getParameter("p_old_food_price");
        String old_quantityStr = request.getParameter("p_old_food_quantity");
        String old_food_description = request.getParameter("p_old_food_description");
        String old_createDate = request.getParameter("p_old_create_date");
        String old_food_status = request.getParameter("p_old_food_status");
        String old_cate_id = request.getParameter("p_old_cate_id");
        String desc;
        {//create description
            desc = "Update: ";
            if(!old_dataImg.equals(data_image)) {
                desc += "Image change: true; ";
            }
            desc += "Food ID: " + food_id + "; ";
            if(!old_food_name.equals(food_name)) {
                desc += "Food Name change FROM " + old_food_name + " TO " + food_name + "; "; 
            }
            if(!old_priceStr.equals(priceStr)) {
                desc += "Price change FROM " + old_priceStr + " TO " + priceStr + "; "; 
            }
            if(!old_quantityStr.equals(quantityStr)) {
                desc += "Quantity change FROM " + old_quantityStr + " TO " + quantityStr + "; "; 
            }
            if(!old_food_description.equals(food_description)) {
                desc += "Description change FROM " + old_food_description + " TO " + food_description + "; "; 
            }
            if(!old_food_status.equals(food_status != null ? "true" : "false")) {
                desc += "Food Status change FROM " + old_food_status + " TO " + food_status + "; "; 
            }
            desc += "Create Date(Time) change FROM " + old_createDate + " TO " + new Date().toString() + "; ";
            if(!old_cate_id.equals(cate_id)) {
                desc += "Food Name change FROM " + old_cate_id + " TO " + cate_id + "; ";
            }
        }
        
        try {
            double food_price = Double.parseDouble(priceStr);
            int food_quantity = Integer.parseInt(quantityStr);
            boolean status = food_status != null;
            FoodDTO food = new FoodDTO(food_id, food_name, data_image, food_description, food_price, food_quantity, new Date(), cate_id, status);
            FoodDAO dao = new FoodDAO();
            dao.updateFood(food);
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute("USERNAME");
            String userid = (String) session.getAttribute("USERID");
            
            RecordHistoryDAO recordDao = new RecordHistoryDAO();
            recordDao.record(userid, username, desc);
            
        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }
        
        request.getRequestDispatcher(SEARCH).forward(request, response);
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
