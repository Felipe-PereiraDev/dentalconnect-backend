package com.github.felipe_pereiradev.dentalconnect.enums;

public enum AppointmentStatusEnum {

    SCHEDULED("Agendado"),
    CONFIRMED("Confirmado"),
    IN_PROGRESS("Em atendimento"),
    COMPLETED("Finalizado"),
    CANCELED("Cancelado"),
    NO_SHOW("Faltou");

    private final String description;

    private AppointmentStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
