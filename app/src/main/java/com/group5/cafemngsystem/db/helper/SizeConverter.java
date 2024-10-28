package com.group5.cafemngsystem.db.helper;

import androidx.room.TypeConverter;

import com.group5.cafemngsystem.db.entity.enum1.Size;

public class SizeConverter {
    @TypeConverter
    public static Size fromString(String value) {
        return value == null ? null : Size.valueOf(value);
    }

    @TypeConverter
    public static String sizeToString(Size size) {
        return size == null ? null : size.name();
    }
}
