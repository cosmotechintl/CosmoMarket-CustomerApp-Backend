package com.cosmo.futsalBooking.generateBookingList.service.impl;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.ConnectionRequestModel;
import com.cosmo.common.util.ConnectorUtil;
import com.cosmo.common.util.ResponseUtil;
import com.cosmo.futsalBooking.generateBookingList.businessHourBookingMapper.BusinessHourBookingMapper;
import com.cosmo.futsalBooking.generateBookingList.entity.Booking;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourDetailModel;
import com.cosmo.futsalBooking.generateBookingList.repo.BookingRepository;
import com.cosmo.futsalBooking.generateBookingList.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BusinessHourBookingMapper businessHourBookingMapper;

    @Override
    public Mono<ApiResponse<List<BusinessHourDetailModel>>> getAllBookings(BusinessHourBookingModel businessHourBookingModel) {


        String path = "/businessHour";
        ParameterizedTypeReference<ApiResponse<List<BusinessHourDetailModel>>> typeRef = new ParameterizedTypeReference<>() {};

        ConnectionRequestModel<BusinessHourBookingModel, List<BusinessHourDetailModel>> connectionRequestModel = ConnectorUtil.parseToConnectionRequestModel(path, businessHourBookingModel, typeRef);

        WebClient webClient = WebClient.create("http://localhost:8091");

        Mono<ApiResponse<List<BusinessHourDetailModel>>> responseMono = ConnectorUtil.fetchFromExternalService(webClient, connectionRequestModel);

        responseMono.subscribe(response -> {
            List<BusinessHourDetailModel> businessHours = response.getData();
            for (BusinessHourDetailModel businessHour : businessHours) {

                LocalTime startTime = LocalTime.parse(businessHour.getStartTime());
                LocalTime endTime = LocalTime.parse(businessHour.getEndTime());

                while (!startTime.isAfter(endTime)) {
                    Booking booking = businessHourBookingMapper.toBookingModel(businessHour, startTime, 1);
                    bookingRepository.save(booking);
                    startTime = startTime.plusHours(1);
                }
            }
        });
        return Mono.just(ResponseUtil.getSuccessfulApiResponse("Booking added successfully"));
    }
}
