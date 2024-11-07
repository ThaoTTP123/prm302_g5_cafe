package com.group5.cafemngsystem.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.entity.enum1.OrderStatus;
import com.group5.cafemngsystem.db.viewModel.OrderViewModel;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    private EditText txtAddress;
    private EditText txtPhone;
    private EditText txtNote;
    private Button btnCheckout;

    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private OrderViewModel mOrderViewModel;

    private void bindingView() {
        txtAddress = findViewById(R.id.etAddress);
        txtPhone = findViewById(R.id.etPhone);
        txtNote = findViewById(R.id.etNote);
        btnCheckout = findViewById(R.id.buttonPlaceOrder);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("cafemngsystem", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    }

    ;

    private void bindingAction() {
        btnCheckout.setOnClickListener(this::onBtnCheckoutClick);
    }

    private void onBtnCheckoutClick(View view) {
        String orderDtoJson = sharedPreferences.getString("orderDto", "");
        OrderDto orderDto = gson.fromJson(orderDtoJson, OrderDto.class);
        int userId = orderDto.getUser().getId();
        String address = txtAddress.getText().toString();
        String phone = txtPhone.getText().toString();
        String note = txtNote.getText().toString();
        // Validate phone number
        if (!isPhoneValid(phone)) {
            Toast.makeText(this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("UserID", "onBtnCheckoutClick: "+userId);
        Order order = new Order(new Date(), userId, address, phone, note, OrderStatus.PENDING);
        mOrderViewModel.update(order);
        orderDto.setOrderDetails(null);
        editor.putString("orderDto", gson.toJson(orderDto));
        editor.apply();
        Intent intent = new Intent(this, OrderHistoryActivity.class);
        startActivity(intent);
    };
    private boolean isPhoneValid(String phone) {
        // Check if phone number is not empty and consists only of digits
        return phone != null && phone.matches("\\d{10,15}"); // Adjust the regex as per your requirements
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}