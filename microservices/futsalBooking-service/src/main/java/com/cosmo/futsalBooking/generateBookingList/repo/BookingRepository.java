package com.cosmo.futsalBooking.generateBookingList.repo;

import com.cosmo.futsalBooking.generateBookingList.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
