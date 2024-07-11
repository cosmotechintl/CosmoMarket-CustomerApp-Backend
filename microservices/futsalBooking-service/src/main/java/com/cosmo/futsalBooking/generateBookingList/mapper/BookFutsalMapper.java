package com.cosmo.futsalBooking.generateBookingList.mapper;

import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.futsalBooking.generateBookingList.entity.FutsalBooking;
import com.cosmo.futsalBooking.generateBookingList.model.BookFutsalModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookFutsalMapper {

    @Autowired
    protected CustomerRepository customerRepository;

    public FutsalBooking bookFutsal(BookFutsalModel model) {

        FutsalBooking futsalBooking = new FutsalBooking();
        futsalBooking.setDate(model.getDate());
        futsalBooking.setStartTime(model.getStartTime());
        futsalBooking.setEndTime(model.getEndTime());
        futsalBooking.setAmount("1200");
        futsalBooking.setVendorCode(model.getVendorCode());
        futsalBooking.setFutsalCode(model.getFutsalCode());
        futsalBooking.setFirstName(model.getFirstName());
        futsalBooking.setLastName(model.getLastName());
        futsalBooking.setEmail(model.getEmail());
        futsalBooking.setMobileNumber(model.getMobileNumber());

        return futsalBooking;
    }
}
