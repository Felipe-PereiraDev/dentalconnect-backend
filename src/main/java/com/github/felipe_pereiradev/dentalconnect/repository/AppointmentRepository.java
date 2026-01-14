package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
