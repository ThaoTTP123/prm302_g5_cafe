package com.group5.cafemngsystem.dbo;

import com.group5.cafemngsystem.db.entity.enum1.Role;
import com.group5.cafemngsystem.db.entity.User;

import java.io.Serializable;

public class UserDto implements Serializable {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private Role role;

    public UserDto() {
    }

    public UserDto(int id, String username, String password, String fullname, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullname = user.getFullname();
        this.role = user.getRole();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
