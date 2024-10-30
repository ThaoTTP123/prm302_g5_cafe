package com.group5.cafemngsystem.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.adapter.customer.OrderItemAdapter;
import com.group5.cafemngsystem.dbo.OrderDetailDto;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView txtOrderId;
    private TextView txtOrderDate;
    private TextView txtOrderStatus;

    private OrderDto orderDto;

    private RecyclerView rcvOrders;

    private void bindingView(){
        txtOrderId = findViewById(R.id.textViewOrderIndex);
        txtOrderDate = findViewById(R.id.textViewOrderDate);
        txtOrderStatus = findViewById(R.id.textViewOrderStatus);
        rcvOrders = findViewById(R.id.recyclerViewOrderItems);
    };
    private void bindingAction(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        receiveIntent();
        initRcvOrders();
    }

    private void receiveIntent() {
        Intent intent = getIntent();
        orderDto = (OrderDto) intent.getSerializableExtra("orderDto");
    }

    private void initRcvOrders() {
        List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetails();
        OrderItemAdapter adapter = new OrderItemAdapter(orderDetailDtos);
        rcvOrders.setAdapter(adapter);
        rcvOrders.setLayoutManager(new GridLayoutManager(this, 1));
    }
}