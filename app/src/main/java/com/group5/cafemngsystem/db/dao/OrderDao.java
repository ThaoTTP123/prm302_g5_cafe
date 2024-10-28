package com.group5.cafemngsystem.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.group5.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.group5.cafemngsystem.db.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM order_table WHERE user = :userId")
    @Transaction
    LiveData<List<OrderWithUserWithOrderDetail>> getOrderByUser(int userId);
}

