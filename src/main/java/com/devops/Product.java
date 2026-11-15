package com.devops;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Product {
    static int lastId=1;
    static ArrayList<Integer> productsIds = new ArrayList<Integer>();
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
        id = lastId;
        productsIds.add(id);
        lastId++;
    }

    public Boolean checkProductExistById(int id){
        return productsIds.contains(id);
    }
}
