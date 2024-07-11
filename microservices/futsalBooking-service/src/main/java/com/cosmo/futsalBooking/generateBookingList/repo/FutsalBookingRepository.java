package com.cosmo.futsalBooking.generateBookingList.repo;

import com.cosmo.futsalBooking.generateBookingList.entity.FutsalBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FutsalBookingRepository extends JpaRepository<FutsalBooking, Long> {

    List<FutsalBooking> findByDateAndVendorCodeAndFutsalCodeAndStartTimeLessThanAndEndTimeGreaterThan(
            LocalDate date, String vendorCode, String futsalCode, LocalTime endTime, LocalTime startTime);
}
