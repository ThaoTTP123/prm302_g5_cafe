package com.group5.cafemngsystem.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.adapter.customer.OrderDetailAdapter;
import com.group5.cafemngsystem.dbo.OrderDetailDto;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rcvCheckout;
    private TextView txtTotal;
    private Button btnCheckout;

    private OrderDto orderDto;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private void bindingView() {
        rcvCheckout = findViewById(R.id.rcvCheckout);
        txtTotal = findViewById(R.id.txtTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        sharedPreferences = getSharedPreferences("cafemngsystem", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void bindingAction() {
        btnCheckout.setOnClickListener(this::onBtnCheckoutClick);
    }

    private void onBtnCheckoutClick(View view) {
        if(orderDto == null) {
            Toast.makeText(this, "Please add drinks to your cart", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        initTxtTotal();
        initRcvCheckout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTxtTotal();
        initRcvCheckout();
    }

    private void initTxtTotal() {
        String orderDtoJson = sharedPreferences.getString("orderDto", null);
        if (orderDtoJson == null) return;
        orderDto = new Gson().fromJson(orderDtoJson, OrderDto.class);
        if (orderDto == null) return;
        if (orderDto.getOrderDetails() == null) return;
        List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetails();
        double total = 0;
        for (OrderDetailDto orderDetailDto : orderDetailDtos) {
            total += (orderDetailDto.getDrink().getPrice() + orderDetailDto.getTopping().getValue()) * orderDetailDto.getSize().getValue() * orderDetailDto.getQuantity();
        }
        txtTotal.setText("Total: $" + String.valueOf(total));
    }

    private void initRcvCheckout() {
        String orderDtoJson = sharedPreferences.getString("orderDto", null);
        if (orderDtoJson == null) return;
        orderDto = new Gson().fromJson(orderDtoJson, OrderDto.class);
        if (orderDto == null) {
            List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
            OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderDetailDtos, -1, this::initTxtTotal);
            rcvCheckout.setAdapter(orderDetailAdapter);
            rcvCheckout.setLayoutManager(new GridLayoutManager(this, 1));
        } else{
            if (orderDto.getOrderDetails() == null){
                List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderDetailDtos, orderDto.getId(),  this::initTxtTotal);
                rcvCheckout.setAdapter(orderDetailAdapter);
                rcvCheckout.setLayoutManager(new GridLayoutManager(this, 1));
                return;
            }
            OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderDto.getOrderDetails(), orderDto.getId(), this::initTxtTotal);
            rcvCheckout.setAdapter(orderDetailAdapter);
            rcvCheckout.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }
}