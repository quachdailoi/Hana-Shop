/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
public class CheckoutServlet extends HttpServlet {
    final String HOME_PAGE = "SearchServlet";
    final String VIEW_CART = "view_cart.jsp";
    final String LOGIN_PAGE = "login.jsp";
    final String PLACE_ORDER_PAGE = "check_out.jsp";
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
        
        String url = PLACE_ORDER_PAGE;
        
        HttpSession session = request.getSession(false);
        
        
        try {
            if(session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                Boolean role = (Boolean) session.getAttribute("ROLE");
                FoodDAO dao= new FoodDAO();
                if(role != null) {
                    if(role) {
                        url = HOME_PAGE;
                        request.setAttribute("NOTIFY", "Login with Customer account for shopping!");
                    } else {
                        if(cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                            boolean isValidCart = dao.checkValidCart(cart).isEmpty();
                            if(isValidCart) {
                                //go to place order;
                            } else {
                                request.setAttribute("VALID_CART", dao.checkValidCart(cart));
                                request.setAttribute("NOTIFY", "Have food in your cart with unavailable quantity can buy! Hover on red place to see more!");
                                url = VIEW_CART;
                            }
                        } else {
                            request.setAttribute("NOTIFY", "Empty cart! Please go to shopping!");
                            url = HOME_PAGE;
                        }
                    }
                } else {
                    url = LOGIN_PAGE;
                    request.setAttribute("NOTIFY", "Login for shopping");
                }
            } else {
                request.getSession();
                request.setAttribute("LAST_RESOURCE", VIEW_CART);
                url = LOGIN_PAGE;
                request.setAttribute("NOTIFY", "Login for shopping");
            }
        } catch (NamingException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
