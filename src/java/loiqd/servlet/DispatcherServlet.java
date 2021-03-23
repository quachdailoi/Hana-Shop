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
import javax.servlet.http.HttpSession;
import loiqd.cate.CateDAO;

/**
 *
 * @author GF65
 */
public class DispatcherServlet extends HttpServlet {

    final String LOGIN_PAGE = "login.jsp";
    final String LOGIN = "LoginServlet";
    final String HOME_PAGE = "SearchServlet";
    final String CREATE = "CreateFoodServlet";
    final String CREATE_PAGE = "detail_control_create.jsp";
    final String LOGOUT = "LogoutServlet";
    final String DELETE = "DeleteFoodServlet";
    final String SEARCH = "SearchServlet";
    final String UPDATE = "UpdateServlet";
    final String DETAILS = "GotoDetailServlet";
    final String ADD_TO_CART = "AddFoodToCartServlet";
    final String VIEW_CART = "view_cart.jsp";
    final String CHECKOUT = "CheckoutServlet";
    final String UPDATE_CART = "UpdateCartServlet";
    final String REMOVE_FROM_CART = "RemoveFoodFromCartServlet";
    final String PLACE_ORDER = "PlaceOrderServlet";
    final String CREATE_ACCOUNT = "RegisterServlet";
    final String HISTORY = "HistoryServlet";
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

        String action = request.getParameter("btnAction");

        String url = HOME_PAGE;        
        
        if (action == null) {
            url = SEARCH;
        } else if ("Log in".equals(action)) {
            url = LOGIN_PAGE;
        } else if ("Log out".equals(action)) {
            url = LOGOUT;
        } else if ("Delete".equals(action)) {
            url = checkAdmin(request, DELETE, HOME_PAGE);
        } else if ("Search".equals(action)) {
            url = SEARCH;
        } else if ("Update".equals(action)) {
            url = checkAdmin(request, UPDATE, HOME_PAGE);
        } else if ("Details".equals(action)) {
            url = DETAILS;
        } else if ("Create New Food".equals(action)) {
            CateDAO dao = new CateDAO();
            try {
                request.setAttribute("LIST_CATE", dao.getCates());
            } catch (NamingException ex) {
                log("Error at " + this.getServletName() + ": " + ex.getMessage());
            } catch (SQLException ex) {
                log("Error at " + this.getServletName() + ": " + ex.getMessage());
            }
            url = checkAdmin(request, CREATE_PAGE, HOME_PAGE);
        } else if ("Create".equals(action)) {
            url = checkAdmin(request, CREATE, HOME_PAGE);
        } else if ("Add To Cart".equals(action)) {
            url = ADD_TO_CART;
        } else if ("Cart".equals(action)) {
            url = VIEW_CART;
        } else if ("Checkout".equals(action)) {
            url = checkAdmin(request, HOME_PAGE, CHECKOUT);
        } else if ("Update Cart".equals(action)) {
            url = checkAdmin(request, HOME_PAGE, UPDATE_CART);
        } else if("Remove".equals(action)){
            url = checkAdmin(request, HOME_PAGE, REMOVE_FROM_CART);
        } else if ("Place Order".equals(action)) {
            url = PLACE_ORDER;
        } else if ("Create New Account".equals(action)) {
            url = CREATE_ACCOUNT;
        } else if("Real Login".equals(action)) {
            url = LOGIN;
        } else if("History".equals(action)) {
            url = HISTORY;
        }
        

        request.getRequestDispatcher(url)
                .forward(request, response);
    }

    private String checkAdmin(HttpServletRequest request, String adminLink, String userLink) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object objIsRole = session.getAttribute("ROLE");
            if (objIsRole != null) {
                boolean isRole = (Boolean) objIsRole;
                if (isRole) {
                    return adminLink;
                }
            }
        }
        return userLink;
    }
    
    private String checkCustomer(HttpServletRequest request, String cusLink, String homeLink) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object objIsRole = session.getAttribute("ROLE");
            if (objIsRole != null) {
                boolean isRole = (Boolean) objIsRole;
                if (!isRole) {
                    return cusLink;
                }
            }
        }
        return homeLink;
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
