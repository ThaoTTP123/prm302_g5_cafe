package com.group5.cafemngsystem.db.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.entity.OrderDetail;
import com.group5.cafemngsystem.db.entity.User;

import java.util.List;

public class OrderWithUserWithOrderDetail {
    @Embedded
    public Order order;

    @Relation(
            parentColumn = "user",
            entityColumn = "id"
    )
    public User user;
}
