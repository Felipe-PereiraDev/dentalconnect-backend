package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleTime;
import com.github.felipe_pereiradev.dentalconnect.dto.schedule.ScheduleUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import com.github.felipe_pereiradev.dentalconnect.exception.ForbiddenException;
import com.github.felipe_pereiradev.dentalconnect.exception.ResourceNotFoundException;
import com.github.felipe_pereiradev.dentalconnect.mapper.ScheduleMapper;
import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.model.Dentist;
import com.github.felipe_pereiradev.dentalconnect.model.Schedule;
import com.github.felipe_pereiradev.dentalconnect.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DentistService dentistService;
    private final ScheduleMapper scheduleMapper;

    public ScheduleResponseDTO create(UUID clinicId, UUID dentistId, ScheduleRequestDTO data) {
        Dentist dentist = dentistService.findByDentistIdAndClinicId(dentistId, clinicId);
        Clinic clinic = dentist.getClinic();
        Schedule schedule = new Schedule(clinic, dentist, data.dateAt(), data.startsAt(), data.endsAt());
        if (scheduleRepository.existsOverlap(dentist.getId(), data.dateAt(), data.startsAt(), data.endsAt())) {
            throw new ConflictException("schedule time unavailable");
        }
        scheduleRepository.save(schedule);
        return scheduleMapper.toResponseDTO(schedule);
    }

    public Map<LocalDate, List<ScheduleTime>> findAllByDentistId(UUID dentistId) {
        Dentist dentist = dentistService.findById(dentistId);
        List<Schedule> scheduleList = scheduleRepository.findAllByDentist(dentist);

        Map<LocalDate, List<ScheduleTime>> map = new HashMap<>();

        for (Schedule schedule : scheduleList) {
            LocalDate date = schedule.getDateAt();
            ScheduleTime scheduleTime = new ScheduleTime(schedule.getId(), schedule.getStartsAt(), schedule.getEndsAt());

            map.computeIfAbsent(date, d -> new ArrayList<>())
                    .add(scheduleTime);
        }

        return map;
    }

    public void deleteById(UUID scheduleId, UUID dentistId) {
        Schedule schedule = findById(scheduleId);
        dentistService.findById(dentistId);

        validateDentist(schedule, dentistId);

        scheduleRepository.deleteById(scheduleId);
    }

    public Schedule findById(UUID id) {
        return scheduleRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("schedule not found")
                );
    }

    public ScheduleResponseDTO update(UUID scheduleId, UUID dentistId, ScheduleUpdateDTO data) {
        Schedule schedule = findById(scheduleId);
        dentistService.findById(dentistId);

        validateDentist(schedule, dentistId);

        if (areStartAndEndTimesEqual(schedule, data)) {
            throw new ConflictException("the new start and end times are identical to the existing schedule");
        }

        schedule.update(data.startsAt(), data.endsAt());
        if (scheduleRepository.existsOverlap(scheduleId, dentistId, schedule.getDateAt(), schedule.getStartsAt(), schedule.getEndsAt())) {
            throw new ConflictException("schedule time unavailable");
        }
        scheduleRepository.save(schedule);
        return scheduleMapper.toResponseDTO(schedule);
    }

    private void validateDentist(Schedule schedule, UUID dentistId) {
        if (!schedule.getDentist().getId().equals(dentistId)) {
            throw new ForbiddenException("you don't have permission to update this schedule.");
        }
    }

    public boolean areStartAndEndTimesEqual(Schedule schedule, ScheduleUpdateDTO dto) {

        if (dto.startsAt() != null && dto.endsAt() != null) {
            return schedule.getStartsAt().equals(dto.startsAt())
                    && schedule.getEndsAt().equals(dto.endsAt());
        }
        return false;
    }
}
