package com.group5.cafemngsystem.dbo;

import com.group5.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.group5.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.entity.enum1.OrderStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto implements Serializable {
    private int id;
    private List<OrderDetailDto> orderDetail; // Foreign key reference
    private Date date;
    private UserDto user; // User identifier
    private String address;
    private String phone;
    private String note;
    private OrderStatus orderStatus;

    public OrderDto() {
    }

    public OrderDto(int id, List<OrderDetailDto> orderDetail, Date date, UserDto user, String address, String phone, String note, OrderStatus orderStatus) {
        this.id = id;
        this.orderDetail = orderDetail;
        this.date = date;
        this.user = user;
        this.address = address;
        this.phone = phone;
        this.note = note;
        this.orderStatus = orderStatus;
    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.date = order.getDate();

        UserDto userDto = new UserDto();
        userDto.setId(order.getUser());
        this.user = userDto;
    }

    public OrderDto(OrderWithUserWithOrderDetail order) {
        this.id = order.order.getId();
        this.date = order.order.getDate();
        this.address = order.order.getAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderDetailDto> getOrderDetails() {
        return orderDetail;
    }

    public void setOrderDetails(List<OrderDetailDto> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
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
