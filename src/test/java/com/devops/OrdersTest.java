package com.devops;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {
    private Order order;

    @BeforeEach
    void init(){
        order = new Order();
    }

    @AfterEach
    void flush(){
        order = null;
    }


}