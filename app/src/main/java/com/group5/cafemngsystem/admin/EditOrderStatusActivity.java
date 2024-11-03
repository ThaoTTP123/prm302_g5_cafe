package com.group5.cafemngsystem.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.entity.Order;
import com.group5.cafemngsystem.db.entity.enum1.Category;
import com.group5.cafemngsystem.db.entity.enum1.OrderStatus;
import com.group5.cafemngsystem.db.viewModel.OrderViewModel;
import com.group5.cafemngsystem.dbo.OrderDto;

public class EditOrderStatusActivity extends AppCompatActivity {

    private Order order;
    private TextView txtId;
    private TextView txtDate;
    private Spinner spnStatus;
    private Button btnUpdate;

    private OrderViewModel mOrderViewModel;

    private void bindingView() {
        txtId = findViewById(R.id.textAdminViewOrderId);
        txtDate = findViewById(R.id.textViewOrderDate);
        spnStatus = findViewById(R.id.spinnerOrderStatus);
        btnUpdate = findViewById(R.id.buttonUpdateStatus);

        setupSpinnerData();
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    }

    private void setupSpinnerData() {
        OrderStatus[] enumValues = OrderStatus.values();
        String[] items = new String[enumValues.length];
        for (int i = 0; i < enumValues.length; i++) {
            items[i] = enumValues[i].name();  // Use name() or toString() for the enum name
        }

        // Step 2: Set up ArrayAdapter with the String array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Step 3: Apply the adapter to the spinner
        spnStatus.setAdapter(adapter);
    }

    ;

    private void bindingAction() {
        btnUpdate.setOnClickListener(this::onBtnUpdateClick);
    }

    private void onBtnUpdateClick(View view) {
        order.setOrderStatus(OrderStatus.values()[spnStatus.getSelectedItemPosition()]);
        mOrderViewModel.update(order);
        Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_order_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        receiveIntent();
    }

    private void receiveIntent() {
        order = (Order) getIntent().getSerializableExtra("order");
        txtId.setText("Order #" + order.getId());
        txtDate.setText(order.getDate().toString());
        spnStatus.setSelection(order.getOrderStatus().ordinal());
    }
}