/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.cart.Cart;
import loiqd.food.FoodDAO;
import loiqd.food.FoodDTO;

/**
 *
 * @author GF65
 */
public class AddFoodToCartServlet extends HttpServlet {

    final String SEARCH = "SearchServlet";
    final String GOTO_DETAIL = "GotoDetailServlet";

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

        String url = SEARCH;
        FoodDAO dao = new FoodDAO();
        String foodId = request.getParameter("p_food_id");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("CART");
        HashMap<String, FoodDTO> cartDetail = new HashMap<>();
        if (cart == null) {
            cart = new Cart();
        }
        try {
            if (foodId != null && dao.getFoodByID(foodId) != null) {
                cart.addFoodToCart(foodId);
                session.setAttribute("CART", cart);
            }

            cartDetail = dao.getListFoodForCart(cart.getItems());
            String userID = (String) session.getAttribute("USERID");
            TreeSet<FoodDTO> listFoodSuggest = dao.getSuggestFood(userID, cart);
            session.setAttribute("SUGGEST_LIST", listFoodSuggest);
            
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }

        session.setAttribute("CART_DETAIL", cartDetail);
        String from = request.getParameter("from");
        
        if(from != null && !from.equals("view_cart.jsp")) {
            url = GOTO_DETAIL;
        } else if(from != null && from.equals("view_cart.jsp")) {
            url = "view_cart.jsp";
        }

        request.getRequestDispatcher(url).forward(request, response);
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
