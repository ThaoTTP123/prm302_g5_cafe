package com.group5.cafemngsystem.db.helper;

import androidx.room.TypeConverter;

import com.group5.cafemngsystem.db.entity.enum1.Role;

public class RoleConverter {

    @TypeConverter
    public static String fromRole(Role role) {
        return role.name();
    }

    @TypeConverter
    public static Role toRole(String role) {
        return Role.valueOf(role);
    }
}
