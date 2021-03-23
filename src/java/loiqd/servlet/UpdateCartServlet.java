/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeSet;
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
public class UpdateCartServlet extends HttpServlet {

    final String VIEW_CART = "view_cart.jsp";

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

        String[] food_ids = request.getParameterValues("p_food_id_U");
        String[] quantities = request.getParameterValues("p_food_quantity");

        int[] food_quantity = new int[0];

        if (food_ids != null) {
            food_quantity = new int[food_ids.length];
            for (int i = 0; i < food_ids.length; i++) {
                try {
                    food_quantity[i] = Integer.parseInt(quantities[i]);
                } catch (NumberFormatException ex) {
                    food_quantity[i] = 0;
                }
            }
        }

        try {
            HttpSession session = request.getSession(false);
            FoodDAO dao = new FoodDAO();
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    cart.updateCart(food_ids, food_quantity);
                    session.setAttribute("CART", cart);
                    HashMap<String, FoodDTO> cartDetail = dao.getListFoodForCart(cart.getItems());
                    if (cartDetail != null) {
                        session.setAttribute("CART_DETAIL", cartDetail);
                    }

                    String userID = (String) session.getAttribute("USERID");
                    TreeSet<FoodDTO> listFoodSuggest = dao.getSuggestFood(userID, cart);
                    session.setAttribute("SUGGEST_LIST", listFoodSuggest);
                }
            }
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }

        request.getRequestDispatcher(VIEW_CART).forward(request, response);
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
