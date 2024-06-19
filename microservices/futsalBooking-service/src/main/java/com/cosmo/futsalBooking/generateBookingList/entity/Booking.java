package com.cosmo.futsalBooking.generateBookingList.entity;

import com.cosmo.common.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking extends AbstractEntity {
    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_booked")
    private Boolean isBooked;

    @Column(name = "amount")
    private String amount;

    public void calculateAndSetAmount() {
        boolean isPeakHour = (
                (startTime.isAfter(LocalTime.of(5, 59)) && startTime.isBefore(LocalTime.of(8, 1))) ||
                        (startTime.isAfter(LocalTime.of(16, 59)) && startTime.isBefore(LocalTime.of(21, 1))));

        if (isPeakHour) {
            this.amount = "1200";
        } else {
            this.amount = "1000";
        }
    }
}
