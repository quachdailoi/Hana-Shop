/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.food.FoodDAO;
import loiqd.record_history.RecordHistoryDAO;

/**
 *
 * @author GF65
 */
public class DeleteFoodServlet extends HttpServlet {
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
        
        String[] foodIds = request.getParameterValues("cbx_food_id");
        
        FoodDAO dao = new FoodDAO();
        try {
        //create record history
            HttpSession session = request.getSession();
            String user_id = (String) session.getAttribute("USERID");
            String user_name = (String) session.getAttribute("USERNAME");
        //get deleted list food name
            ArrayList<String> listFoodName = new ArrayList<>();
            for (String foodId : foodIds) {
                listFoodName.add(dao.getNameByID(foodId));
            }
        // create desc
            String desc = "Delete Food (id - name): ";
            for (int i = 0; i < listFoodName.size(); i++) {
                String pair = "(" + foodIds[i] + " - " + listFoodName.get(i) + "); ";
                desc += pair;
            }
        // write record to DB
            RecordHistoryDAO recordDao = new RecordHistoryDAO();
            recordDao.record(user_id, user_name, desc);
        // delete Food in DB
            dao.deleteFoods(foodIds);
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (NamingException ex) {
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
