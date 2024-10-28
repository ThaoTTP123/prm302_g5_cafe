package com.group5.cafemngsystem.db.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.entity.OrderDetail;

public class OrderDetailWithDrink {
    @Embedded
    public OrderDetail orderDetail;

    @Relation(
            parentColumn = "drinkId",
            entityColumn = "id"
    )
    public Drink drink;
}
