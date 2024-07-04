package com.cosmo.futsalBooking.generateBookingList.repo;

import com.cosmo.futsalBooking.generateBookingList.entity.FutsalBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FutsalBookingRepository extends JpaRepository<FutsalBooking, Long> {
}
