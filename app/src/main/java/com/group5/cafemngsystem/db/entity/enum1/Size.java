package com.group5.cafemngsystem.db.entity.enum1;

public enum Size {
    //(S - 1, M - 1.2, L - 1.5) (hệ số để nhân vào giá)

    S(1),
    M(1.2),
    L(1.5);

    private double value;

    Size(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }
}
