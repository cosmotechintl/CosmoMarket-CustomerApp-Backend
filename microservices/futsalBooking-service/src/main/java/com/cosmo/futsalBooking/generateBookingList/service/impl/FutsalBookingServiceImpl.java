package com.cosmo.futsalBooking.generateBookingList.service.impl;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.constant.ServiceConstant;
import com.cosmo.common.exception.NotFoundException;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.service.AbstractConnectorService;
import com.cosmo.common.service.ConnectorService;
import com.cosmo.common.service.PropertiesFileValue;
import com.cosmo.common.util.ResponseUtil;
import com.cosmo.futsalBooking.generateBookingList.businessHourBookingMapper.BusinessHourBookingMapper;
import com.cosmo.futsalBooking.generateBookingList.entity.FutsalBooking;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHour;
import com.cosmo.futsalBooking.generateBookingList.repo.FutsalBookingRepository;
import com.cosmo.futsalBooking.generateBookingList.service.FutsalBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier(ServiceConstant.FUTSAL_SERVICE)
@Slf4j
public class FutsalBookingServiceImpl extends AbstractConnectorService implements FutsalBookingService, ConnectorService {
    private final FutsalBookingRepository futsalBookingRepository;
    private final BusinessHourBookingMapper businessHourBookingMapper;
    private final PropertiesFileValue propertiesFileValue;

    @Override
    public String getBaseUrl() {
        return propertiesFileValue.getVendorServiceUrl();
    }

    public Mono<ApiResponse> createBookingsLists(BusinessHourBookingModel businessHourBookingModel) {
        return connectToService(businessHourBookingModel, ApiConstant.BUSINESSHOUR, new ParameterizedTypeReference<ApiResponse<List<BusinessHour>>>() {
        })
                .flatMap(apiResponse -> {
                    if (apiResponse.getHttpStatus() == HttpStatus.OK) {
                        List<BusinessHour> businessHours = apiResponse.getData();
                        List<FutsalBooking> futsalBookings = generateBookings(businessHours);
                        futsalBookingRepository.saveAll(futsalBookings);
                        return Mono.just(ResponseUtil.getSuccessfulApiResponse("Booking slots created and saved successfully"));
                    } else {
                        return Mono.error(new NotFoundException("Failed to fetch business hours"));
                    }
                });
    }

    public List<FutsalBooking> generateBookings(List<BusinessHour> businessHours) {
        List<FutsalBooking> allFutsalBookingSlots = new ArrayList<>();

        for (BusinessHour businessHour : businessHours) {
            LocalTime startTime = LocalTime.parse(businessHour.getStartTime());
            LocalTime endTime = LocalTime.parse(businessHour.getEndTime());

            while (startTime.isBefore(endTime)) {
                FutsalBooking futsalBooking = businessHourBookingMapper.toBookingModel(businessHour, startTime, 1);
                allFutsalBookingSlots.add(futsalBooking);
                startTime = startTime.plusHours(1);
            }
        }

        return allFutsalBookingSlots;
    }
}
