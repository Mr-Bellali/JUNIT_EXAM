package com.devops;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Order {

    private String reference;
    private String studentId;
    private String productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;
    private BigDecimal rentalAmount;
    private BigDecimal deposit;
    private BigDecimal totalAmount;

    public Order(String reference, String studentId,
                 String productId, LocalDate startDate,
                 LocalDate endDate, long duration,
                 BigDecimal rentalAmount, BigDecimal deposit,
                 BigDecimal totalAmount) {

        this.reference = reference;
        this.studentId = studentId;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.rentalAmount = rentalAmount;
        this.deposit = deposit;
        this.totalAmount = totalAmount;
    }

}
