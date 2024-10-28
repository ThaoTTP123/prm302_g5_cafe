package com.group5.cafemngsystem.db.entity.enum1;

public enum Topping {
    //(Boba - 0.2, Almond - 0.5, Cheese - 0.3) (+ vào giá (đơn vị usd))
    None(0),
    Boba(0.2),
    Almond(0.5),
    Cheese(0.3);
    private double value;
    Topping(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }
}
