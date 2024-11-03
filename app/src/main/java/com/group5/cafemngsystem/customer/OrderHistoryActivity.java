package com.group5.cafemngsystem.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.adapter.customer.OrderAdapter;
import com.group5.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.group5.cafemngsystem.db.viewModel.OrderViewModel;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rcvOrderHistory;

    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private OrderViewModel mOrderViewModel;

    private void bindingView(){
        rcvOrderHistory = findViewById(R.id.recyclerViewOrders);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("cafemngsystem", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    };
    private void bindingAction(){};

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, CustomerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order History");
        setContentView(R.layout.activity_order_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        initRcvOrderHistory();
    }

    private void initRcvOrderHistory() {
        String orderDtoJson = sharedPreferences.getString("orderDto", "");
        OrderDto orderDto = gson.fromJson(orderDtoJson, OrderDto.class);

        mOrderViewModel.getOrderByUser(orderDto.getUser().getId()).observe(this, orders -> {
            List<OrderDto> orderDtos = new ArrayList<>();
            for(OrderWithUserWithOrderDetail order : orders){
                orderDtos.add(new OrderDto(order));
            }
            OrderAdapter orderAdapter = new OrderAdapter(orderDtos);
            rcvOrderHistory.setAdapter(orderAdapter);
            rcvOrderHistory.setLayoutManager(new GridLayoutManager(this, 1));
        });
    }
}