package com.group5.cafemngsystem.dbo;

import com.group5.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.group5.cafemngsystem.db.entity.OrderDetail;
import com.group5.cafemngsystem.db.entity.enum1.Size;
import com.group5.cafemngsystem.db.entity.enum1.Topping;

import java.io.Serializable;

public class OrderDetailDto implements Serializable {
    private int id;
    private DrinkDto drink;
    private int quantity;
    private Size size;
    private Topping topping;


    public OrderDetailDto(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        DrinkDto drinkDto = new DrinkDto();
        drinkDto.setId(orderDetail.getId());
        this.drink = drinkDto;
        this.quantity = orderDetail.getQuantity();
        this.size = orderDetail.getSize();
        this.topping = orderDetail.getTopping();
    }

    public OrderDetailDto(int id, DrinkDto drink, int quantity, Size size, Topping topping) {
        this.id = id;
        this.drink = drink;
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
    }

    public OrderDetailDto() {

    }

    public OrderDetailDto(OrderDetailWithDrink orderDetail) {
        this.id = orderDetail.orderDetail.getId();
        this.drink = new DrinkDto(orderDetail.drink);
        this.quantity = orderDetail.orderDetail.getQuantity();
        this.size = orderDetail.orderDetail.getSize();
        this.topping = orderDetail.orderDetail.getTopping();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DrinkDto getDrink() {
        return drink;
    }

    public void setDrink(DrinkDto drink) {
        this.drink = drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }
}
