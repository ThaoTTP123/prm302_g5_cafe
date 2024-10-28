package com.group5.cafemngsystem.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.group5.cafemngsystem.db.entity.enum1.Size;
import com.group5.cafemngsystem.db.entity.enum1.Topping;
import com.group5.cafemngsystem.db.helper.SizeConverter;
import com.group5.cafemngsystem.db.helper.ToppingConverter;
import com.group5.cafemngsystem.dbo.OrderDetailDto;

@Entity(tableName = "order_detail",
        foreignKeys = {
                @ForeignKey(
                        entity = Drink.class,
                        parentColumns = "id",
                        childColumns = "drinkId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Order.class,
                        parentColumns = "id",
                        childColumns = "orderId",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    @TypeConverters(SizeConverter.class)
    private Size size;
    @TypeConverters(ToppingConverter.class)
    private Topping topping;

    private int drinkId;

    private int orderId;

    public OrderDetail(OrderDetailDto orderDetailDto) {
        this.quantity = orderDetailDto.getQuantity();
        this.size = orderDetailDto.getSize();
        this.topping = orderDetailDto.getTopping();
        this.drinkId = orderDetailDto.getDrink().getId();
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderDetail(int quantity, Size size, Topping topping, int drinkId, int orderId) {
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
        this.drinkId = drinkId;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
