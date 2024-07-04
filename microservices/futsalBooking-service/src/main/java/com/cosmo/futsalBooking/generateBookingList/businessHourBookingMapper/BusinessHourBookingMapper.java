package com.cosmo.futsalBooking.generateBookingList.businessHourBookingMapper;

import com.cosmo.futsalBooking.generateBookingList.entity.FutsalBooking;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHour;
import com.cosmo.futsalBooking.generateBookingList.repo.FutsalBookingRepository;
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
    private FutsalBookingRepository futsalBookingRepository;
    public FutsalBooking toBookingModel(BusinessHour businessHour, LocalTime startTime, int daysInFuture) {
        FutsalBooking futsalBooking = new FutsalBooking();
        futsalBooking.setVendorCode(businessHour.getVendorCode());
        futsalBooking.setDate(findNearestDate(businessHour.getDay(), daysInFuture));
        futsalBooking.setStartTime(startTime);
        futsalBooking.setEndTime(startTime.plusHours(1));
        futsalBooking.setIsBooked(false);
        futsalBooking.calculateAndSetAmount();
        return futsalBooking;
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
