package com.github.felipe_pereiradev.dentalconnect.service;

import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentRequestDTO;
import com.github.felipe_pereiradev.dentalconnect.dto.appointment.AppointmentResponseDTO;
import com.github.felipe_pereiradev.dentalconnect.exception.BadRequestException;
import com.github.felipe_pereiradev.dentalconnect.exception.UnprocessableEntityException;
import com.github.felipe_pereiradev.dentalconnect.model.*;
import com.github.felipe_pereiradev.dentalconnect.repository.AppointmentRepository;
import com.github.felipe_pereiradev.dentalconnect.utils.GeoDistanceUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final ClinicService clinicService;
    private final DentistService dentistService;
    private final ClinicProcedureService clinicProcedureService;

    @Transactional
    public AppointmentResponseDTO create(UUID clinicId, UUID patientId, AppointmentRequestDTO data) {
        Patient patient = patientService.findById(patientId);
        Clinic clinic = clinicService.findById(clinicId);
        ClinicProcedure clinicProcedure = clinicProcedureService.findByClinicIdAndProcedureId(clinicId, data.procedureId());
        Dentist dentist = chooseDentist(clinicProcedure);

        if (isNotWithinRadius(clinic, patient, data.radiusKm())) {
            throw new BadRequestException("clinic fora do raio de distância");
        }

        Appointment appointment = new Appointment(
                data.date(),
                data.hour(),
                clinic,
                dentist,
                patient,
                clinicProcedure.getPrice(),
                clinicProcedure
        );
        return null;
    }

    private boolean isNotWithinRadius(Clinic clinic, Patient patient, double radiusKm) {
        Address clinicAddress = clinic.getAddress();
        Address patientAddress = patient.getAddress();
        double resultRadiusKm = GeoDistanceUtils.calculateRadiusKm(
                clinicAddress.getLatitude(),
                clinicAddress.getLongitude(),
                patientAddress.getLatitude(),
                patientAddress.getLongitude()
        );
        return !(radiusKm < resultRadiusKm);
    }

    private Dentist chooseDentist(ClinicProcedure clinicProcedure) {
        List<DentistClinicProcedure> list = clinicProcedure.getDentistClinicProcedureList()
                .stream()
                .filter(DentistClinicProcedure::isActive)
                .toList();

        if (list.isEmpty()) {
            throw new UnprocessableEntityException("No dentist available for this procedure");
        }

        if (list.size() == 1) {
            return list.getFirst().getDentist();
        }
        int chosenIndex = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(chosenIndex).getDentist();
    }

}
