package com.group5.cafemngsystem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.adapter.admin.AdminDrinkAdapter;
import com.group5.cafemngsystem.db.entity.Drink;
import com.group5.cafemngsystem.db.viewModel.DrinkViewModel;
import com.group5.cafemngsystem.dbo.DrinkDto;

import java.util.ArrayList;
import java.util.List;

public class AdminListDrinkActivity extends AppCompatActivity {

    private RecyclerView rcvDrink;
    private FloatingActionButton btnAddDrink;

    private DrinkViewModel mDrinkViewModel;

    private void bindingView() {
        rcvDrink = findViewById(R.id.recyclerViewDrinks);
        btnAddDrink = findViewById(R.id.fabAddDrink);

        mDrinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);
    };

    private void bindingAction(){
        btnAddDrink.setOnClickListener(this::onAddDrinkClick);
    }

    private void onAddDrinkClick(View view) {
        Intent intent = new Intent(this, CreateDrinkActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_drink);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Drink Management");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        initRcvDrink();
    }

    private void initRcvDrink() {
        mDrinkViewModel.getAllDrinks().observe(this, drinks -> {
            List<DrinkDto> drinkDtos = new ArrayList<>();
            for(Drink drink : drinks)
                drinkDtos.add(new DrinkDto(drink));
            AdminDrinkAdapter adapter = new AdminDrinkAdapter(drinkDtos);
            rcvDrink.setAdapter(adapter);
            rcvDrink.setLayoutManager(new GridLayoutManager(this, 1));
        });
    }
}