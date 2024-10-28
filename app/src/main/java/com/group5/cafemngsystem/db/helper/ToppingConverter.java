package com.group5.cafemngsystem.db.helper;

import androidx.room.TypeConverter;

import com.group5.cafemngsystem.db.entity.enum1.Topping;

import java.util.ArrayList;
import java.util.List;

public class ToppingConverter {
    @TypeConverter
    public static Topping fromString(String value) {
        return value == null ? null : Topping.valueOf(value);
    }

    @TypeConverter
    public static String toppingListToString(Topping toppings) {
        return toppings == null ? null : toppings.name();
    }
}
