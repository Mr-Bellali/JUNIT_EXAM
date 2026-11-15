package com.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {
    private ReservationService reservationService;
    private Product mockedProduct;
    private MockedStatic<Student> studentMock;
    private MockedStatic<Product> productMock;

    private final LocalDate today = LocalDate.now();

    @BeforeEach
    void setup() {
        reservationService = new ReservationService();

        /* Mock static methods */
        studentMock = mockStatic(Student.class);
        productMock = mockStatic(Product.class);

        // By default it will be true
        studentMock.when(() -> Student.checkStudentExistsById(1)).thenReturn(true);
        productMock.when(() -> Product.checkProductExistById(1)).thenReturn(true);

//        Create a mocked product
        mockedProduct = mock(Product.class);

        when(mockedProduct.getDailyRentalRate()).thenReturn(new BigDecimal("10.00"));

        when(mockedProduct.getDeposit()).thenReturn(new BigDecimal("100.00"));

        productMock.when(() -> Product.getProductById(1)).thenReturn(mockedProduct);
    }

    @AfterEach
    void flush() {
        studentMock.close();
        productMock.close();
    }

    //    RG01: Invalid inputs
    @Test
    void shouldRejectInvalidStudentId() {
        assertThrows(Exception.class, () -> reservationService.makeReservation((0, 1, today, today.plusDays(1))));

        assertEquals(0, reservationService.getOrders().size());
    }

    @Test
    void shouldRejectNullDates() {
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, null, today));
        assertEquals(0, reservationService.getOrders().size());
    }

    //    RG02: Start date cannot be in the past
    @Test
    void shouldRejectPastStartDate() {
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, today.minusDays(1), today));
        assertEquals(0, reservationService.getOrders().size());
    }
}