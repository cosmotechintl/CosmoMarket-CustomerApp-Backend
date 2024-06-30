package com.cosmo.futsalBooking.futsal.model;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FetchFutsalDetail extends ModelBase {
    @NotBlank(message = "VendorCode is required.")
    private String vendorCode;
}
