package com.group5.cafemngsystem.dbo;

import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.entity.enum1.Category;

import java.io.Serializable;

public class DrinkDto implements Serializable {
    private int id;
    private String name;
    private int img;
    private double price;
    private Category category;

    public DrinkDto(int id, String name, int img, double price, Category category) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public DrinkDto(Drink drink) {
        this.id = drink.getId();
        this.name = drink.getName();
        this.img = drink.getImg();
        this.price = drink.getPrice();
        this.category = drink.getCategory();
    }

    public DrinkDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
