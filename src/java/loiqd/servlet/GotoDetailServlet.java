/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.cate.CateDAO;
import loiqd.cate.CateDTO;
import loiqd.food.FoodDAO;
import loiqd.food.FoodDTO;

/**
 *
 * @author GF65
 */
public class GotoDetailServlet extends HttpServlet {

    final String DETAILS_CUS = "detail_cus.jsp";
    final String DETAILS_ADMIN = "detail_control.jsp";
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

        String foodId = request.getParameter("p_food_id");
        String search_name = request.getParameter("p_search_name");
        String search_cate = request.getParameter("p_search_cate");
        String search_price = request.getParameter("p_search_price");
        
        
        String url = DETAILS_CUS;
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            String fullName = (String) session.getAttribute("USERNAME");
            Boolean role = (Boolean) session.getAttribute("ROLE");
            if (fullName != null && role != null && role) {
                url = DETAILS_ADMIN;
            }
        }

        FoodDAO dao = new FoodDAO();
        CateDAO cateDao = new CateDAO();

        try {
            FoodDTO food = dao.getFoodByID(foodId);
            ArrayList<CateDTO> listCate = cateDao.getCates();
            if (food == null) {
                //if food not found in db go back to index.jsp, page: curPage.
                url = HOME_PAGE;
            } else {
                // right condition goto detail with attr FOOD and attr for Search bar
                request.setAttribute("FOOD", food);
                request.setAttribute("LIST_CATE", listCate);
                request.setAttribute("SEARCH_NAME", search_name);
                request.setAttribute("SEARCH_CATE", search_cate);
                request.setAttribute("SEARCH_PRICE", search_price);
            }

        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (SQLException ex) {
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
