package com.example.hitcapp.models;

public class ImageResponse {
    private String image; // Chuỗi base64
    private String type;  // image/jpeg

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
