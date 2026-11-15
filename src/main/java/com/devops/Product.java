
package com.devops;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Product {

    static int lastId = 1;
    static ArrayList<Integer> productsIds = new ArrayList<>();
    static ArrayList<Product> products = new ArrayList<>();

    private final int id;
    private final String name;
    private final String description;
    private final BigDecimal dailyRentalRate;
    private final BigDecimal deposit;

    public Product(String name, String description,
                   BigDecimal dailyRentalRate, BigDecimal deposit) {

        if (dailyRentalRate == null || dailyRentalRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Daily rental rate must be positive");
        }

        if (deposit == null || deposit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Deposit cannot be negative");
        }

        this.name = name;
        this.description = description;
        this.dailyRentalRate = dailyRentalRate;
        this.deposit = deposit;

        this.id = lastId++;

        productsIds.add(id);
        products.add(this);
    }

    public int getId() {
        return id;
    }

    public BigDecimal getDailyRentalRate() {
        return dailyRentalRate;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    static Boolean checkProductExistById(int id) {
        return productsIds.contains(id);
    }

    static Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
