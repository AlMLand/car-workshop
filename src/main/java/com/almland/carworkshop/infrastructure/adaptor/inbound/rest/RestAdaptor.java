package com.almland.carworkshop.infrastructure.adaptor.inbound.rest;

import com.almland.carworkshop.application.port.inbound.RestPort;
import com.almland.carworkshop.domain.WorkShopOffer;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.dto.AppointmentRestDTO;
import com.almland.carworkshop.infrastructure.adaptor.inbound.rest.mapper.RestMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping(path = "/werkstatt")
public class RestAdaptor {
    private RestPort restPort;
    private RestMapper restMapper;

    public RestAdaptor(RestPort restPort, RestMapper restMapper) {
        this.restPort = restPort;
        this.restMapper = restMapper;
    }

    @GetMapping(path = "/{werkstattId}/termin")
    public ResponseEntity<Set<AppointmentRestDTO>> getAllAppointments(
            @PathVariable(name = "werkstattId") UUID workShopId,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE_TIME) LocalDateTime until,
            @RequestParam(required = false) WorkShopOffer workShopOffer
    ) {
        var appointments = restPort.getAllAppointments(workShopId, from, until, workShopOffer);
        return appointments.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(restMapper.mapToAppointmentDto(appointments));
    }

    ;

    @GetMapping(path = "/{werkstattId}/termin/{terminId}")
    public ResponseEntity<AppointmentRestDTO> getAppointment(
            @PathVariable(name = "werkstattId") UUID workShopId,
            @PathVariable(name = "terminId") UUID appointmentId
    ) {
        var appointment = restPort.getAppointment(workShopId, appointmentId);
        return appointment == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(
                        restMapper.mapToAppointmentDto(Set.of(appointment)).stream().findFirst().orElseThrow()
                );
    }
}
