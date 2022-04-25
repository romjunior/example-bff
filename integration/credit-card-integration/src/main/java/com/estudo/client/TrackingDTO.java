package com.estudo.client;

import java.time.LocalDate;
import java.util.List;

record TrackingDTO(String address, List<StepDTO> steps) {
    record StepDTO(String address, LocalDate date) { }
}
