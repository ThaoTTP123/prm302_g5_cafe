package com.group5.cafemngsystem.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
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

import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.adapter.customer.DrinkAdapter;
import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.viewModel.DrinkViewModel;
import com.group5.cafemngsystem.dbo.DrinkDto;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView rcvDrinks;

    private List<DrinkDto> drinkList;

    private DrinkViewModel mDrinkViewModel;

//    private

    private void bindingView() {
        rcvDrinks = findViewById(R.id.rcvDrinks);

        mDrinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);
    }

    private void bindingAction() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.history) {
            Intent intent = new Intent(CustomerActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logout_customer) {
            finish();
        } else if (item.getItemId() == R.id.cart) {
            Intent intent = new Intent(CustomerActivity.this, CartActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.profile) {
            Intent intent = new Intent(CustomerActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        initRcvDrinks();
    }

    private void initRcvDrinks() {
        drinkList = new ArrayList<>();
        mDrinkViewModel.getAllDrinks().observe(this, drinks -> {
            for (Drink drink : drinks) {
                DrinkDto drinkDto = new DrinkDto(drink);
                drinkList.add(drinkDto);
            }
            DrinkAdapter adapter = new DrinkAdapter(drinkList);
            rcvDrinks.setAdapter(adapter);
            rcvDrinks.setLayoutManager(new GridLayoutManager(this, 2));
        });
    }
}