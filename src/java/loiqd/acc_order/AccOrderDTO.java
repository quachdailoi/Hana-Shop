/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.acc_order;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author GF65
 */
public class AccOrderDTO implements Serializable{
    private String orderId;
    private String userId;
    private double amount;
    private Date orderDate;
    private String status;
    private String address;
    private String email;
    private String method;
    private String recevierName;

    public AccOrderDTO(String orderId, String userId, double amount, Date orderDate, String status, String address, String email, String method, String recevierName) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.email = email;
        this.method = method;
        this.recevierName = recevierName;
    }

    public AccOrderDTO() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRecevierName() {
        return recevierName;
    }

    public void setRecevierName(String recevierName) {
        this.recevierName = recevierName;
    }
    
}
