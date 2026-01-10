package com.github.felipe_pereiradev.dentalconnect.controller;

import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleTime;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping(value = "/clinics/{clinicId}/dentists/{dentistId}")
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@PathVariable("clinicId") UUID clinicId,
                                                              @PathVariable("dentistId") UUID dentistId,
                                                              @RequestBody @Validated ScheduleRequestDTO data) {
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.create(clinicId, dentistId, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleResponseDTO);
    }

    @GetMapping(value = "/dentists/{dentistId}/schedules")
    public ResponseEntity<Map<LocalDate, List<ScheduleTime>>> getSchedulesByDentist(@PathVariable("dentistId") UUID dentistId) {
        return ResponseEntity.ok(scheduleService.findAllByDentistId(dentistId));
    }

    @DeleteMapping(value = "/dentists/{dentistId}/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable("dentistId") UUID dentistId,
                                                                                 @PathVariable("scheduleId") UUID scheduleId) {
        scheduleService.deleteById(scheduleId, dentistId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/dentists/{dentistId}/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable("dentistId") UUID dentistId,
                                                              @PathVariable("scheduleId") UUID scheduleId,
                                                              @RequestBody ScheduleUpdateDTO data) {
        ScheduleResponseDTO updatedSchedule = scheduleService.update(scheduleId, dentistId, data);
        return ResponseEntity.ok(updatedSchedule);
    }
}
