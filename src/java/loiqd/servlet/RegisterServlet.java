/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.account.AccountDAO;

/**
 *
 * @author GF65
 */
public class RegisterServlet extends HttpServlet {

    final String SEARCH = "SearchServlet";
    final String CREATE_AGAIN = "create.jsp";

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

        AccountDAO dao = new AccountDAO();

        String userName = request.getParameter("p_user_name");
        String userId = request.getParameter("p_user_id");
        String favorCateId = request.getParameter("cbbxCate");

        String url = SEARCH;
        if(userId == null || userId.isEmpty() || userName == null || userName.isEmpty() || favorCateId == null || favorCateId.isEmpty()) {
            url = CREATE_AGAIN;
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("USERNAME", userName);
        session.setAttribute("USERID", userId);
        
        boolean role = false;
        session.setAttribute("ROLE", role);

        try {
            dao.create(userId, null, userName, role, favorCateId);
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());

        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());

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
