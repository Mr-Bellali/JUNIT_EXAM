package com.devops;

import java.math.BigDecimal;

public class Product {
    static int lastId=0;
    int id;
    String name;
    String description;
    BigDecimal dailyRentalRate;
    BigDecimal deposit;

    public Product(String name, String description, BigDecimal dailyRentalRate, BigDecimal deposit){
        this.name = name;
        this.description = description;
        this.dailyRentalRate = dailyRentalRate;
        this.deposit = deposit;
    }
}
