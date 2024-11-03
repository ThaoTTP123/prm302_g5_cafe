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
import com.group5.cafemngsystem.adapter.admin.AdminUserAdapter;
import com.group5.cafemngsystem.db.entity.User;
import com.group5.cafemngsystem.db.viewModel.UserViewModel;
import com.group5.cafemngsystem.dbo.UserDto;

import java.util.ArrayList;
import java.util.List;

public class AdminListUserActivity extends AppCompatActivity {

    private RecyclerView rcvUser;

    private UserViewModel mUserViewModel;

    private void bindingView() {
        rcvUser = findViewById(R.id.recyclerViewUsers);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List User");
        setContentView(R.layout.activity_list_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        initRcvUser();
    }

    private void initRcvUser() {
        mUserViewModel.getAllUsers().observe(this, users -> {
            List<UserDto> userDtos = new ArrayList<>();
            for(User user : users)
                userDtos.add(new UserDto(user));
            AdminUserAdapter adapter = new AdminUserAdapter(userDtos);
            rcvUser.setAdapter(adapter);
            rcvUser.setLayoutManager(new GridLayoutManager(this, 1));
        });
    }
}