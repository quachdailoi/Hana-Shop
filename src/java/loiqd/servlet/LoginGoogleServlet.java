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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.account.AccountDAO;
import loiqd.cate.CateDAO;
import loiqd.login_gg.GooglePojo;
import loiqd.login_gg.GoogleUtils;

/**
 *
 * @author GF65
 */
@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    final String HOME_PAGE = "SearchServlet";
    final String CREATE = "create.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;

    public LoginGoogleServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        String url = HOME_PAGE;
        if (code == null || code.isEmpty()) {
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");
            dis.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            String userName = (String) session.getAttribute("USERNAME");
            if (userName == null) {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);

                AccountDAO dao = new AccountDAO();
                try {
                    if (dao.isNewAcc(googlePojo.getEmail())) {
                        session.setAttribute("USERID", googlePojo.getEmail());
                        session.setAttribute("ROLE", false);
                        CateDAO cateDao = new CateDAO();
                        request.setAttribute("LIST_CATE", cateDao.getCates());
                        url = CREATE;
                    } else {
                        session.setAttribute("USERID", dao.getUserName(googlePojo.getEmail()));
                    }
                } catch (NamingException ex) {
                    log("Error at " + this.getServletName() + ": " + ex.getMessage());
                } catch (SQLException ex) {
                    log("Error at " + this.getServletName() + ": " + ex.getMessage());
                }

                request.getRequestDispatcher(url).forward(request, response);
            } else {
                request.getRequestDispatcher(HOME_PAGE).forward(request, response);
            }
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
