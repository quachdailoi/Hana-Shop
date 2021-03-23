/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loiqd.cart;

import java.util.HashMap;

/**
 *
 * @author GF65
 */
public class Cart {
    private HashMap<String, Integer> items;

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }
    
    public void addFoodToCart(String title) {
        if(items == null) {
            items = new HashMap<>();
        }
        int quantity = 1;
        if(items.containsKey(title)) {
            quantity = items.get(title) + 1;
        }
        
        items.put(title, quantity);
    }
    public void removeFoodFromCart(String foodId) {
        if(items == null) {
            return;
        }
        if(items.containsKey(foodId)) {
            items.remove(foodId);
            if(items.isEmpty()) {
                items = null;
            }
        }
    }
    
    public void updateCart(String[] ids, int[] quantities) {
        items = new HashMap<>();
        
        for (int i = 0; i < ids.length; i++) {
            items.put(ids[i], quantities[i]);
        }
    }
}
