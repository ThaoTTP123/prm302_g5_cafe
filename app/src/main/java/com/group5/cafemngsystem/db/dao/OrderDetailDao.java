package com.group5.cafemngsystem.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.group5.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.group5.cafemngsystem.db.entity.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    long insert(OrderDetail orderDetail);

    @Update
    void update(OrderDetail orderDetail);

    @Delete
    void delete(OrderDetail orderDetail);

    @Query("SELECT * FROM order_detail")
    LiveData<List<OrderDetailWithDrink>> getAllOrderDetails();

    @Query("SELECT * FROM order_detail WHERE id = :detailId")
    LiveData<OrderDetailWithDrink> getOrderDetailById(int detailId);

    @Query("SELECT * FROM order_detail WHERE orderId = :orderId")
    LiveData<List<OrderDetailWithDrink>> getOrderDetailListByOrderId(int orderId);
}

