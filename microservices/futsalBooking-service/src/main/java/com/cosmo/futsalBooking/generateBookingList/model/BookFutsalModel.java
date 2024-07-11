package com.cosmo.futsalBooking.generateBookingList.model;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookFutsalModel extends ModelBase {

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String vendorCode;

    private String futsalCode;

    @NotNull(message = "Please select a date")
    private LocalDate date;

    @NotNull(message = "Please select a start time")
    private LocalTime startTime;

    @NotNull(message = "Please select a end time")
    private LocalTime endTime;

}
