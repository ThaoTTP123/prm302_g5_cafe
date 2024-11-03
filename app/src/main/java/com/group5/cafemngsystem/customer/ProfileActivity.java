package com.group5.cafemngsystem.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.viewModel.UserViewModel;
import com.group5.cafemngsystem.dbo.OrderDto;
import com.group5.cafemngsystem.dbo.UserDto;

public class ProfileActivity extends AppCompatActivity {

    private UserDto userDto;
    private OrderDto orderDto;

    private TextView txtUsername;
    private EditText txtFname;
    private Button btnSave;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private UserViewModel mUserViewModel;

    private void bindingView(){
        txtUsername = findViewById(R.id.txtCusUsername);
        txtFname = findViewById(R.id.edtCusFname);
        btnSave = findViewById(R.id.btnSaveProfile);

        sharedPreferences = getSharedPreferences("cafemngsystem", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        String orderDtoJson = sharedPreferences.getString("orderDto", "");
        if (!orderDtoJson.isEmpty()) {
            orderDto = gson.fromJson(orderDtoJson, OrderDto.class);
            userDto = orderDto.getUser();
            txtUsername.setText(userDto.getUsername());
            txtFname.setText(userDto.getFullname());
        }
    };
    private void bindingAction(){
        btnSave.setOnClickListener(this::setOnClickBtnSave);
    }

    private void setOnClickBtnSave(View view) {
        String fullname = txtFname.getText().toString();
        userDto.setFullname(fullname);
        mUserViewModel.update(userDto);
        orderDto.setUser(userDto);
        editor.putString("orderDto", gson.toJson(orderDto));
        editor.apply();
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }
}