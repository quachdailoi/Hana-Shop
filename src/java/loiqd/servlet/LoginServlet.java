/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
public class LoginServlet extends HttpServlet {

    final String SEARCH_PAGE = "SearchServlet";
    final String ERROR_PAGE = "login.jsp";

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

        String url = ERROR_PAGE;

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                String userName = (String) session.getAttribute("USERNAME");
                if (userName == null) {
                    String userID = request.getParameter("p_user_id");
                    String password = request.getParameter("p_user_password");
                    String last_resource = request.getParameter("p_last_resource");

                    AccountDAO dao = new AccountDAO();
                    if (dao.checkLogin(userID, password)) {
                        session.setAttribute("USERID", userID);
                        session.setAttribute("USERNAME", dao.getUserName());
                        session.setAttribute("ROLE", dao.isRole());

                        url = SEARCH_PAGE;
                        if (last_resource != null) {
                            url = last_resource;
                        }
                    } else {
                        request.setAttribute("INVALID", "true");
                        request.setAttribute("NOTIFY", "Wrong USer ID or Password!");
                    }
                } else {
                   url = SEARCH_PAGE;
                }
            }
        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }

        if (SEARCH_PAGE.equals(url)) {
            response.sendRedirect(url);

        } else {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
