package com.group5.cafemngsystem.db.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.group5.cafemngsystem.db.entity.User;
import com.group5.cafemngsystem.db.entity.enum1.Role;
import com.group5.cafemngsystem.db.repository.UserRepository;
import com.group5.cafemngsystem.dbo.UserDto;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private final LiveData<List<User>> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAll();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public void insert(User word) { mRepository.insert(word); }

    public LiveData<User> getByUsername(String username) {
        return mRepository.getByUsername(username);
    }

    public void update(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String fullname = userDto.getFullname();
        Role role = userDto.getRole();
        User user = new User(username, password, fullname, role);
        user.setId(userDto.getId());
        mRepository.update(user);
    }
}
