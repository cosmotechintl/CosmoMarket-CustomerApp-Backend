package com.cosmo.futsalBooking.generateBookingList.service.impl;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
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
import com.cosmo.futsalBooking.generateBookingList.mapper.BookFutsalMapper;
import com.cosmo.futsalBooking.generateBookingList.model.BookFutsalModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHour;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.repo.FutsalBookingRepository;
import com.cosmo.futsalBooking.generateBookingList.service.FutsalBookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier(ServiceConstant.FUTSAL_SERVICE)
@Slf4j
public class FutsalBookingServiceImpl extends AbstractConnectorService implements FutsalBookingService, ConnectorService {
    private final FutsalBookingRepository futsalBookingRepository;
    private final BusinessHourBookingMapper businessHourBookingMapper;
    private final PropertiesFileValue propertiesFileValue;
    private final BookFutsalMapper bookFutsalMapper;
    private final CustomerRepository customerRepository;

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

    @Override
    @Transactional
    public Mono<ApiResponse> bookFutsal(BookFutsalModel bookFutsalModel, Principal connectedUser) {
        Optional<Customer> checkCustomer = customerRepository.findByUsername(connectedUser.getName());
        if (checkCustomer.isPresent()) {
            Customer customer = checkCustomer.get();
            bookFutsalModel.setFirstName(customer.getFirstName());
            bookFutsalModel.setLastName(customer.getLastName());
            bookFutsalModel.setEmail(customer.getEmail());
            bookFutsalModel.setMobileNumber(customer.getMobileNumber());

            Duration duration = Duration.between(bookFutsalModel.getStartTime(), bookFutsalModel.getEndTime());
            if (duration.toHours() < 1) {
                return Mono.just(ResponseUtil.getFailureResponse("Futsal must be booked for at least one hour"));
            }

            List<FutsalBooking> overlappingBookings = futsalBookingRepository.findByDateAndVendorCodeAndFutsalCodeAndStartTimeLessThanAndEndTimeGreaterThan(
                    bookFutsalModel.getDate(), bookFutsalModel.getVendorCode(), bookFutsalModel.getFutsalCode(),
                    bookFutsalModel.getEndTime(), bookFutsalModel.getStartTime());

            if (!overlappingBookings.isEmpty()) {
                return Mono.just(ResponseUtil.getFailureResponse("This slot is already booked"));
            }

            FutsalBooking futsalBooking = bookFutsalMapper.bookFutsal(bookFutsalModel);
            futsalBookingRepository.save(futsalBooking);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Futsal booked successfully"));
        } else {
            return Mono.just(ResponseUtil.getFailureResponse("Futsal book failed"));
        }
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
