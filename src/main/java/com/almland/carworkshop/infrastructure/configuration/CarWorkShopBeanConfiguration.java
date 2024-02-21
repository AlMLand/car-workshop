package com.almland.carworkshop.infrastructure.configuration;

import com.almland.carworkshop.application.port.outbound.PersistencePort;
import com.almland.carworkshop.application.service.AppointmentSuggestionService;
import com.almland.carworkshop.application.service.TimeSlotOverlappingService;
import com.almland.carworkshop.application.service.WorkShopService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarWorkShopBeanConfiguration {

    @Bean
    public TimeSlotOverlappingService timeSlotOverlappingService() {
        return new TimeSlotOverlappingService();
    }

    @Bean
    public AppointmentSuggestionService appointmentSuggestionService(
            TimeSlotOverlappingService timeSlotOverlappingService
    ) {
        return new AppointmentSuggestionService(timeSlotOverlappingService);
    }

    @Bean
    public WorkShopService appointmentService(
            PersistencePort persistencePort,
            AppointmentSuggestionService appointmentSuggestionService
    ) {
        return new WorkShopService(persistencePort, appointmentSuggestionService);
    }
}
