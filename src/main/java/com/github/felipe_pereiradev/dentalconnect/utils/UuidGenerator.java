package com.github.felipe_pereiradev.dentalconnect.utils;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

public class UuidGenerator {
    public static UUID generate() {
        return UuidCreator.getTimeOrderedEpoch();
    }
}