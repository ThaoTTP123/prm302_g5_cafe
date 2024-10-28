package com.group5.cafemngsystem.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.group5.cafemngsystem.db.entity.enum1.Category;
import com.group5.cafemngsystem.db.helper.CategoryConverter;

@Entity(tableName = "drinks")
public class Drink {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int img;
    private double price;
    @TypeConverters(CategoryConverter.class)
    private Category category;

    public Drink(String name, int img, double price, Category category) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.category = category;
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
