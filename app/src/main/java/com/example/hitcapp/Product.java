package com.example.hitcapp;

public class Product {
    private String name;
    private String price;
    private int imageResource;
    private int quantity;

    public Product(String name, String price, int imageResource) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
        this.quantity = 1;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResource() { return imageResource; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}