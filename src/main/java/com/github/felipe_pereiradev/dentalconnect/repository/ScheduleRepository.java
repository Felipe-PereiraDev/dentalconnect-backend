package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Dentist;
import com.github.felipe_pereiradev.dentalconnect.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    @Query("""
            select count(s) > 0
            from Schedule s
            where s.dentist.id = :dentistId
            and s.dateAt = :dateAt
            and :startsAt < s.endsAt
            and :endsAt   > s.startsAt
    """)
    boolean existsOverlap(@Param("dentistId") UUID dentistId,
                          @Param("dateAt") LocalDate dateAt,
                          @Param("startsAt") LocalTime startsAt,
                          @Param("endsAt") LocalTime endsAt);

    List<Schedule> findAllByDentist(Dentist dentist);

    @Query("""
            select count(s) > 0
            from Schedule s
            where s.dentist.id = :dentistId
            and s.id <> :scheduleId
            and s.dateAt = :dateAt
            and :startsAt < s.endsAt
            and :endsAt   > s.startsAt
    """)
    boolean existsOverlap(@Param("scheduleId") UUID scheduleId,
                          @Param("dentistId") UUID dentistId,
                          @Param("dateAt") LocalDate dateAt,
                          @Param("startsAt") LocalTime startsAt,
                          @Param("endsAt") LocalTime endsAt);
}
