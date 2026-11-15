
package com.devops;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationService {

    // RG13: Store all created orders in memory
    private final ArrayList<Order> orders = new ArrayList<>();

    public Order makeReservation(
            int studentId,
            int productId,
            LocalDate startDate,
            LocalDate endDate) throws Exception {

        // RG01: Validate inputs
        if (studentId <= 0 || productId <= 0 ||
                startDate == null || endDate == null) {
            throw new Exception("Invalid inputs");
        }

        // RG02: Start date cannot be in the past
        if (startDate.isBefore(LocalDate.now())) {
            throw new Exception("Start date cannot be in the past");
        }

        // RG03: End date cannot be before start date
        if (endDate.isBefore(startDate)) {
            throw new Exception("End date cannot be before start date");
        }

        // RG05: Check if student and product exist
        if (!Student.checkStudentExistsById(studentId)) {
            throw new Exception("Student doesn't exist");
        }

        if (!Product.checkProductExistById(productId)) {
            throw new Exception("Product doesn't exist");
        }

        Product product = Product.getProductById(productId);

        // RG04: Calculate duration (inclusive)
        long duration = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // RG06: Maximum reservation duration is 7 days
        if (duration > 7) {
            throw new Exception("Maximum reservation duration is 7 days");
        }

        // RG07: Check student reservation quota
        checkStudentQuota(studentId);

        // RG08: Check product availability
        checkProductAvailability(productId, startDate, endDate);

        // RG10 and RG12: Calculate rental amount
        BigDecimal rentalAmount = product.getDailyRentalRate()
                .multiply(BigDecimal.valueOf(duration))
                .setScale(2, RoundingMode.HALF_UP);

        // RG12: Deposit expressed to 2 decimal places
        BigDecimal deposit = product.getDeposit()
                .setScale(2, RoundingMode.HALF_UP);

        // RG11: Calculate total amount
        BigDecimal totalAmount = rentalAmount.add(deposit)
                .setScale(2, RoundingMode.HALF_UP);

        // RG13: Generate a unique reference
        String reference = UUID.randomUUID().toString();

/*
         Create the order
*/
        Order order = new Order(
                reference,
                studentId,
                productId,
                startDate,
                endDate,
                rentalAmount,
                deposit,
                totalAmount
        );

        // RG14: Save only after all validations succeed
        orders.add(order);

        return order;
    }

    /* RG07: A student can have maximum 2 ongoing or future orders */
    private void checkStudentQuota(int studentId) throws Exception {

        LocalDate today = LocalDate.now();

        long activeOrders = orders.stream()
                .filter(o -> o.getStudentId() == studentId)
                .filter(o -> !o.getEndDate().isBefore(today))
                .count();

        if (activeOrders >= 2) {
            throw new Exception(
                    "Student has reached the maximum reservation limit"
            );
        }
    }

    // RG08: Check overlapping reservations for the same product
    private void checkProductAvailability(
            int productId,
            LocalDate startDate,
            LocalDate endDate) throws Exception {

        boolean unavailable = orders.stream()
                .filter(o -> o.getProductId() == productId)
                .anyMatch(o ->
                        !startDate.isAfter(o.getEndDate()) &&
                                !endDate.isBefore(o.getStartDate())
                );

        if (unavailable) {
            throw new Exception(
                    "Product is unavailable during this period"
            );
        }
    }

    public List<Order> getOrders() {
        return List.copyOf(orders);
    }
}
