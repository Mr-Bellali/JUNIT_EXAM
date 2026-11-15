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
        assertThrows(Exception.class, () -> reservationService.makeReservation(0, 1, today, today.plusDays(1)));
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

    // RG03: End date cannot be before start date
    @Test
    void shouldRejectEndDateBeforeStartDate() {
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, today.plusDays(3), today.plusDays(1)));
        assertEquals(0, reservationService.getOrders().size());
    }

    // RG05: Student must exist
    @Test
    void shouldRejectUnknownStudent() {
        studentMock.when(() -> Student.checkStudentExistsById(99)).thenReturn(false);
        assertThrows(Exception.class, () -> reservationService.makeReservation(99, 1, today, today.plusDays(1)));
        assertEquals(0, reservationService.getOrders().size());
    }

    // RG05: Product must exist
    @Test
    void shouldRejectUnknownProduct() {
        productMock.when(() -> Product.checkProductExistById(99)).thenReturn(false);
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 99, today, today.plusDays(1)));
        assertEquals(0, reservationService.getOrders().size());
    }

    // RG06: Maximum duration is 7 days
    @Test
    void shouldRejectReservationLongerThanSevenDays() {
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, today, today.plusDays(7)));
        assertEquals(0, reservationService.getOrders().size());
    }

    @Test
    void shouldAcceptReservationOfExactlySevenDays() throws Exception {
        Order order = reservationService.makeReservation(1, 1, today, today.plusDays(6));
        assertEquals(7, order.getDuration());
    }

    // RG04: Duration includes start and end dates
    @Test
    void shouldCountSameDayReservationAsOneDay() throws Exception {
        Order order = reservationService.makeReservation(1, 1, today, today);
        assertEquals(1, order.getDuration());
    }

    // RG07: Maximum two ongoing or future reservations
    @Test
    void shouldRejectThirdActiveReservation() throws Exception {
        reservationService.makeReservation(1, 1, today.plusDays(1), today.plusDays(1));
        reservationService.makeReservation(1, 1, today.plusDays(3), today.plusDays(3));

        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, today.plusDays(5), today.plusDays(5)));
        assertEquals(2, reservationService.getOrders().size());
    }

    // RG08: Product cannot have overlapping reservations
    @Test
    void shouldRejectOverlappingReservation() throws Exception {
        reservationService.makeReservation(1, 1, today.plusDays(1), today.plusDays(3));
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, today.plusDays(3), today.plusDays(5)));
        assertEquals(1, reservationService.getOrders().size());
    }

    // RG10, RG11 and RG12: Amount calculations
    @Test
    void shouldCalculateAmountsCorrectly() throws Exception {
        Order order = reservationService.makeReservation(1, 1, today, today.plusDays(2));

        assertEquals(new BigDecimal("30.00"), order.getRentalAmount());
        assertEquals(new BigDecimal("100.00"), order.getDeposit());
        assertEquals(new BigDecimal("130.00"), order.getTotalAmount());
    }

    @Test
    void shouldRoundAmountsUsingHalfUp() throws Exception {
        when(mockedProduct.getDailyRentalRate()).thenReturn(new BigDecimal("10.555"));
        when(mockedProduct.getDeposit()).thenReturn(new BigDecimal("5.555"));

        Order order = reservationService.makeReservation(1, 1, today, today.plusDays(2));

        assertEquals(new BigDecimal("31.67"), order.getRentalAmount());
        assertEquals(new BigDecimal("5.56"), order.getDeposit());
        assertEquals(new BigDecimal("37.23"), order.getTotalAmount());
    }

    // RG13: Unique order references
    @Test
    void shouldGenerateDifferentReferences() throws Exception {
        Order firstOrder = reservationService.makeReservation(1, 1, today.plusDays(1), today.plusDays(1));
        Order secondOrder = reservationService.makeReservation(1, 1, today.plusDays(3), today.plusDays(3));

        assertNotEquals(firstOrder.getReference(), secondOrder.getReference());
    }

    // RG14: Failed reservation must not be saved
    @Test
    void shouldNotSaveOrderWhenReservationFails() {
        assertThrows(Exception.class, () -> reservationService.makeReservation(1, 1, today.minusDays(1), today));
        assertTrue(reservationService.getOrders().isEmpty());
    }
}