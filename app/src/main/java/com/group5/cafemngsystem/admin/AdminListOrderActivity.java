package com.group5.cafemngsystem.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.adapter.admin.OrderAdapter;
import com.group5.cafemngsystem.db.viewModel.OrderViewModel;

public class AdminListOrderActivity extends AppCompatActivity {

    private RecyclerView rcvOrders;

    private OrderViewModel mOrderViewModel;

    private void bindingView(){
        rcvOrders = findViewById(R.id.recyclerViewOrders);

        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    };
    private void bindingAction(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List Order");
        setContentView(R.layout.activity_list_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        initRcvOrders();
    }

    private void initRcvOrders() {
        mOrderViewModel.getAllOrders().observe(this, orders -> {
            OrderAdapter adapter = new OrderAdapter(orders);
            rcvOrders.setAdapter(adapter);
            rcvOrders.setLayoutManager(new GridLayoutManager(this, 1));
        });
    }
}