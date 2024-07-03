package com.cosmo.futsalBooking.generateBookingList.model;

import com.cosmo.common.model.ModelBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessHourBookingModel extends ModelBase {
    private String vendorCode;
}
