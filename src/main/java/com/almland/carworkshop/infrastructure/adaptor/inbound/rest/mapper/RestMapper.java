package com.almland.carworkshop.infrastructure.adaptor.inbound.rest.mapper;

import com.almland.carworkshop.domain.Appointment;
import com.almland.carworkshop.domain.Offer;
import com.almland.carworkshop.domain.TimeSlot;
import com.almland.carworkshop.domain.WorkShop;
import com.almland.carworkshop.domain.WorkShopOffer;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.request.AppointmentRequestDTO;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response.AppointmentResponseDTO;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response.AppointmentSuggestionResponseDTO;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.response.TimeSlotResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestMapper {

    public Set<AppointmentResponseDTO> mapToAppointmentDto(Set<Appointment> appointments) {
        return appointments
                .stream()
                .map(appointment ->
                        AppointmentResponseDTO.builder()
                                .workShopId(appointment.getAppointmentId())
                                .start(appointment.getTimeSlot().getStartTime())
                                .end(appointment.getTimeSlot().getEndTime())
                                .workShopOffer(appointment.getWorkShopOffer().getOffer().name())
                                .build()
                )
                .collect(Collectors.toSet());
    }

    public Appointment mapToAppointment(UUID workShopId, AppointmentRequestDTO appointmentRequestDTO) {
        return Appointment.builder()
                .workShop(WorkShop.builder()
                        .workShopId(workShopId)
                        .build())
                .timeSlot(TimeSlot.builder()
                        .startTime(appointmentRequestDTO.getStart())
                        .build())
                .workShopOffer(WorkShopOffer.builder()
                        .offer(Offer.valueOf(appointmentRequestDTO.getWorkShopOffer()))
                        .build())
                .build();
    }

    public Offer mapToOffer(String offer) {
        return offer != null ? Offer.valueOf(offer) : null;
    }

    public AppointmentSuggestionResponseDTO mapToAppointmentSuggestionDto(Set<Appointment> appointments) {
        var timeSlotSuggestions = appointments.stream()
                .map(appointment -> TimeSlotResponseDTO.builder()
                        .startTime(appointment.getTimeSlot().getStartTime())
                        .endTime(appointment.getTimeSlot().getEndTime())
                        .build())
                .sorted(Comparator.comparing(TimeSlotResponseDTO::getStartTime))
                .toList();
        var appointment = appointments.stream().findFirst().orElseThrow();
        return AppointmentSuggestionResponseDTO.builder()
                .workShopId(appointment.getWorkShop().getWorkShopId())
                .workShopOfferId(appointment.getWorkShopOffer().getWorkShopOfferId())
                .possibleTimeSlots(timeSlotSuggestions)
                .build();
    }
}
