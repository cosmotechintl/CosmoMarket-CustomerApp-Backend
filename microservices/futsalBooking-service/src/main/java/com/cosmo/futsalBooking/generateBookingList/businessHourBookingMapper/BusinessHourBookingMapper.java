package com.cosmo.futsalBooking.generateBookingList.businessHourBookingMapper;

import com.cosmo.futsalBooking.generateBookingList.entity.Booking;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourDetailModel;
import com.cosmo.futsalBooking.generateBookingList.repo.BookingRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BusinessHourBookingMapper {
    @Autowired
    private BookingRepository bookingRepository;
    public Booking toBookingModel(BusinessHourDetailModel businessHourDetailModel, LocalTime startTime, int daysInFuture) {
        Booking booking = new Booking();
        booking.setVendorId(businessHourDetailModel.getVendorId());
        booking.setDate(findNearestDate(businessHourDetailModel.getDay(), daysInFuture));
        booking.setStartTime(startTime);
        booking.setEndTime(startTime.plusHours(1));
        booking.setIsBooked(false);
        booking.calculateAndSetAmount();
        return bookingRepository.save(booking);
    }

    private LocalDate findNearestDate(String dayOfWeekStr, int daysInFuture) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekStr.toUpperCase());
        LocalDate futureDate = LocalDate.now().plusDays(daysInFuture);
        while (futureDate.getDayOfWeek() != dayOfWeek) {
            futureDate = futureDate.plusDays(1);
        }
        return futureDate;
    }
}
