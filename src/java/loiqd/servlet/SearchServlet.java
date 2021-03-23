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
import loiqd.food.FoodDAO;
import loiqd.food.FoodDTO;

/**
 *
 * @author GF65
 */
public class SearchServlet extends HttpServlet {

    final String CUS_PAGE = "view_cus.jsp";
    final String ADMIN_PAGE = "view_control.jsp";
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

        String curPageStr = request.getParameter("p_cur_page");
        
        int curPage = 1;
        if (curPageStr != null) {
            try {
                curPage = Integer.parseInt(curPageStr);
            } catch (NumberFormatException ex) {
                curPage = 1;
            }
            if(curPage < 1) curPage = 1;
        }
        
                
        String searchName = request.getParameter("p_search_name");

        String searchCate = request.getParameter("p_search_cate");

        String searchPrice = request.getParameter("p_search_price");
        

        CateDAO dao = new CateDAO();

        try {
            
            request.setAttribute("LIST_CATE", dao.getCates());
            FoodDAO foodDao = new FoodDAO();
            HttpSession session = request.getSession();
            Boolean role = (Boolean) session.getAttribute("ROLE");

            ArrayList<FoodDTO> listFood = foodDao.searchFoods(8, curPage, searchName, searchCate, searchPrice, role);

            int maxPage = foodDao.getPaging();
            if (curPage > maxPage) {
                curPage = maxPage;
            }

            request.setAttribute("LIST_FOOD", listFood);
            request.setAttribute("CUR_PAGE", curPage);
            request.setAttribute("MAX_PAGE", maxPage);

        } catch (NamingException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }

        String url = CUS_PAGE;
        
        HttpSession session = request.getSession(false);
        if(session != null) {
            String userName = (String) session.getAttribute("USERNAME");
            Boolean isRole = (Boolean) session.getAttribute("ROLE");
            if(userName != null && isRole != null && isRole) {
                url = ADMIN_PAGE;
            }
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
