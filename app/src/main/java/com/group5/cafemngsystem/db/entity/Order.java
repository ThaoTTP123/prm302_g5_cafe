package com.group5.cafemngsystem.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.group5.cafemngsystem.db.entity.enum1.OrderStatus;
import com.group5.cafemngsystem.db.helper.DateConverter;
import com.group5.cafemngsystem.db.helper.OrderStatusConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "order_table",
        foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user",
                onDelete = ForeignKey.CASCADE)
        })
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(DateConverter.class)
    private Date date;
    private int user; // User identifier
    private String address;
    private String phone;
    private String note;
    @TypeConverters(OrderStatusConverter.class)
    private OrderStatus orderStatus;

    public Order(Date date, int user, String address, String phone, String note, OrderStatus orderStatus) {
        this.date = date;
        this.user = user;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
