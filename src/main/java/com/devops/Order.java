
package com.devops;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Order {

    private final String reference;
    private final int studentId;
    private final int productId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long duration;
    private final BigDecimal rentalAmount;
    private final BigDecimal deposit;
    private final BigDecimal totalAmount;

    public Order(String reference, int studentId, int productId,
                 LocalDate startDate, LocalDate endDate,
                 BigDecimal rentalAmount, BigDecimal deposit,
                 BigDecimal totalAmount) {

        this.reference = reference;
        this.studentId = studentId;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;

        // Both start and end dates are included
        this.duration = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        this.rentalAmount = rentalAmount;
        this.deposit = deposit;
        this.totalAmount = totalAmount;
    }

    public String getReference() {
        return reference;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getProductId() {
        return productId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getDuration() {
        return duration;
    }

    public BigDecimal getRentalAmount() {
        return rentalAmount;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
