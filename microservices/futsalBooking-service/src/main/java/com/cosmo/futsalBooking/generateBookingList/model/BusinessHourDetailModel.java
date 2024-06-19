package com.cosmo.futsalBooking.generateBookingList.model;

import com.cosmo.common.model.ModelBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusinessHourDetailModel {
    private Long vendorId;
    private String day;
    private String startTime;
    private String endTime;
    private boolean closed;
}
