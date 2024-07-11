package com.cosmo.futsalBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.cosmo",  exclude = FreeMarkerAutoConfiguration.class)
public class FutsalBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutsalBookingServiceApplication.class, args);
	}

}
