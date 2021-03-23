/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import loiqd.acc_order.AccOrderDAO;
import loiqd.acc_order.AccOrderDTO;
import loiqd.food.FoodDAO;
import loiqd.order_detail.OrderFoodDAO;
import loiqd.order_detail.OrderFoodDTO;

/**
 *
 * @author GF65
 */
public class HistoryServlet extends HttpServlet {

    final String HOME_PAGE = "SearchServlet";
    final String HISTORY_PAGE = "history.jsp";

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

        String url = HOME_PAGE;

        String search_name = request.getParameter("p_his_name");
        String search_date = request.getParameter("p_his_date");

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                Boolean role = (Boolean) session.getAttribute("ROLE");
                String userId = (String) session.getAttribute("USERID");
                if (role != null && !role && userId != null) {
                    AccOrderDAO accOrderDao = new AccOrderDAO();
                    OrderFoodDAO orderFoodDao = new OrderFoodDAO();
                    FoodDAO foodDao = new FoodDAO();
                    ArrayList<AccOrderDTO> listOrder = accOrderDao.getAllOrderByUID(userId);

                    //filter listOrder for date
                    ArrayList<AccOrderDTO> filterList = new ArrayList<>();
                    try {
                        if (search_date != null && !search_date.isEmpty()) {
                            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(search_date);
                            filterList = new ArrayList<>();
                            for (AccOrderDTO accOrderDTO : listOrder) {
                                if (accOrderDTO.getOrderDate().equals(searchDate)) {
                                    filterList.add(accOrderDTO);
                                }
                            }
                            listOrder = filterList;
                        }
                    } catch (ParseException ex) {
                    }

                    HashMap<String, ArrayList<OrderFoodDTO>> mapFoods = new HashMap<>(); //map orderID with orderfoodDTO

                    for (AccOrderDTO orderDTO : listOrder) {
                        String orderId = orderDTO.getOrderId();
                        ArrayList<OrderFoodDTO> listFood = orderFoodDao.getFoodOfOrder(orderId);

                        for (OrderFoodDTO food : listFood) {
                            String image = foodDao.getDataImageByID(food.getFoodId());
                            String name = foodDao.getNameByID(food.getFoodId());
                            food.setImage(image);
                            food.setName(name);
                        }

                        mapFoods.put(orderId, listFood);
                    }

                    // filter listOrder for name
                    if (search_name != null && !search_name.isEmpty()) {
                        search_name = search_name.trim().toLowerCase();
                        filterList = new ArrayList<>();
                        for (AccOrderDTO accOrderDTO : listOrder) {
                            String orderId = accOrderDTO.getOrderId();
                            boolean flag = false;
                            if (mapFoods.containsKey(orderId)) { // check each list food belong to an order
                                for (OrderFoodDTO orderFoodDTO : mapFoods.get(orderId)) {
                                    if (orderFoodDTO.getName().toLowerCase().contains(search_name)) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (flag) {
                                filterList.add(accOrderDTO);
                            } else {
                                mapFoods.remove(orderId);
                            }
                        }
                        //update new list for searching
                        listOrder = filterList;
                    }

                    request.setAttribute("LIST_ORDER", listOrder);
                    request.setAttribute("MAP_FOOD", mapFoods);

                    url = HISTORY_PAGE;

                } else {
                    request.setAttribute("NOTIFY", "Login With Customer account to view your shopping history!");
                }
            } else {
                request.setAttribute("NOTIFY", "Login With Customer account to view your shopping history!");
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
