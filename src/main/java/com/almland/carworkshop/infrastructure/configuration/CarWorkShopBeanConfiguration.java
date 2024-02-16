package com.almland.carworkshop.infrastructure.configuration;

import com.almland.carworkshop.application.port.outbound.PersistencePort;
import com.almland.carworkshop.application.service.WorkShopService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarWorkShopBeanConfiguration {
    @Bean
    public WorkShopService appointmentService(PersistencePort persistencePort) {
        return new WorkShopService(persistencePort);
    }
}
