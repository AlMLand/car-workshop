package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.mapper;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.AppointmentRestDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RestMapper {
    public Set<AppointmentRestDTO> mapToAppointmentDto(Set<Appointment> appointments) {
        return appointments
                .stream()
                .map(appointment -> new AppointmentRestDTO.Builder()
                        .workShopId(appointment.getAppointmentId())
                        .start(appointment.getTimeSlot().getStartTime())
                        .end(appointment.getTimeSlot().getEndTime())
                        .workShopOffer(appointment.getWorkShopOffer().getOffer().name())
                        .build()
                ).collect(Collectors.toSet());
    }

    public Appointment mapToAppointment(AppointmentRestDTO appointmentRestDTO) {
        return null;
    }
}
