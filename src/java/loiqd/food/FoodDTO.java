/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.food;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author GF65
 */
public class FoodDTO implements Serializable, Comparable<FoodDTO>{
    private String foodId;
    private String foodName;
    private String imgData;
    private String description;
    private double price;
    private int quantity;
    private Date createDate;
    private String cateId;
    private boolean status;

    public FoodDTO() {
    }

    public FoodDTO(String foodId, String foodName, String imgData, String description, double price, int quantity, Date createDate, String cateId, boolean status) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.imgData = imgData;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
        this.cateId = cateId;
        this.status = status;
    }
    
    public FoodDTO(String foodName, String imgData, double price, int quantityInStore) {
        this.foodName = foodName;
        this.imgData = imgData;
        this.price = price;
        this.quantity = quantityInStore;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImgData() {
        return imgData;
    }

    public void setImgDate(String imgData) {
        this.imgData = imgData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    @Override
    public String toString() {
        return "Food ID: " + foodId + "\tFood Name: " + foodName + "\tDesciption: " + description + ""
        + "\nPrice: " + price + "\tQuantity: " + quantity + "\tCreate date: " + createDate.toString() + "\tCate ID: " + cateId;
    }

    @Override
    public int compareTo(FoodDTO food) {
        
        return this.foodId.compareTo(food.getFoodId());
    }

    
}
