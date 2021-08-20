package com.example.repeatlayoutman;

public class Product {
    private String number;
    private String imageId;
    private String name;
    public Product(String number, String imageId, String name){
            this.number=number;
            this.imageId=imageId;
            this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number =number;
    }

}
