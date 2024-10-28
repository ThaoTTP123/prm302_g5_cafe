package com.group5.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.group5.cafemngsystem.db.entity.OrderDetail;
import com.group5.cafemngsystem.db.entity.enum1.Size;
import com.group5.cafemngsystem.db.entity.enum1.Topping;
import com.group5.cafemngsystem.db.repository.OrderDetailRepository;
import com.group5.cafemngsystem.dbo.DrinkDto;
import com.group5.cafemngsystem.dbo.OrderDetailDto;

import java.util.List;

public class OrderDetailViewModel extends AndroidViewModel {

    private OrderDetailRepository mRepository;
    private final LiveData<List<OrderDetailWithDrink>> mAllOrderDetails;

    public OrderDetailViewModel(Application application) {
        super(application);
        mRepository = new OrderDetailRepository(application);
        mAllOrderDetails = mRepository.getAllOrderDetails();
    }

    public LiveData<List<OrderDetailWithDrink>> getAllOrderDetails() {
        return mAllOrderDetails;
    }

    public long insert(OrderDetail orderDetail) {
        return mRepository.insert(orderDetail);
    }

    public void update(OrderDetailDto orderDetailDto, int orderId) {
        int id = orderDetailDto.getId();;
        DrinkDto drink = orderDetailDto.getDrink();;
        int quantity = orderDetailDto.getQuantity();;
        Size size = orderDetailDto.getSize();
        Topping topping = orderDetailDto.getTopping();
        OrderDetail orderDetail = new OrderDetail(quantity, size, topping, drink.getId(), orderId);
        orderDetail.setId(id);
        mRepository.update(orderDetail);
    }

    public void delete(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = new OrderDetail(orderDetailDto);
        mRepository.delete(orderDetail);
    }

    public LiveData<OrderDetailWithDrink> getOrderDetailById(int detailId) {
        return mRepository.getOrderDetailById(detailId);
    }

    public LiveData<List<OrderDetailWithDrink>> getOrderDetailListByOrderId(int orderId) {
        return mRepository.getOrderDetailListByOrderId(orderId);
    }
}
