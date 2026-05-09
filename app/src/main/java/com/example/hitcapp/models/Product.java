package com.example.hitcapp.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private int qty;
    private int categoryId;
    private String categoryName;
    private String imageBase64;

    public Product() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}
