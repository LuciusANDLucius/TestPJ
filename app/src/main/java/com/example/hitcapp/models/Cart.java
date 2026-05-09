package com.example.hitcapp.models;

import java.util.List;

public class Cart {
    private int id;
    private int userId;
    private String date;
    private List<CartProduct> products;

    public Cart() {}

    public Cart(int userId, String date, List<CartProduct> products) {
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public List<CartProduct> getProducts() { return products; }
    public void setProducts(List<CartProduct> products) { this.products = products; }

    public static class CartProduct {
        private int productId;
        private int quantity;

        public CartProduct(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getProductId() { return productId; }
        public void setProductId(int productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
