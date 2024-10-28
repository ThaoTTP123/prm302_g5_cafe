package com.group5.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.entity.enum1.OrderStatus;
import com.group5.cafemngsystem.db.repository.OrderRepository;
import com.group5.cafemngsystem.dbo.OrderDetailDto;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository mRepository;
    private final LiveData<List<Order>> mAllOrders;

    public OrderViewModel(Application application) {
        super(application);
        mRepository = new OrderRepository(application);
        mAllOrders = mRepository.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return mAllOrders;
    }

    public long insert(OrderDto orderDto) {

        String address = orderDto.getAddress();
        int userId = orderDto.getUser().getId();
        String phone = orderDto.getPhone();
        String note = orderDto.getNote();
        Order order = new Order(new Date(), userId, address, phone, note, OrderStatus.PENDING);
        return mRepository.insert(order);
    }

    public void update(Order order) {
        mRepository.update(order);
    }

    public LiveData<List<OrderWithUserWithOrderDetail>> getOrderByUser(int userId) {
        return mRepository.getOrderByUser(userId);
    }
}
