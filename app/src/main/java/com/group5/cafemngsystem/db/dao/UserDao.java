package com.group5.cafemngsystem.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.group5.cafemngsystem.db.entity.User;
import com.group5.cafemngsystem.db.helper.RoleConverter;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User... user);

    @Update
    void update(User... user);

    @Delete
    void delete(User... user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<User> getByUsername(String username);

}
