package br.purpletech.vivo.dtos.report;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReportDTO(
        Long id,
        LocalDate createdAt,
        int feeling,
        String question,
        String event,
        String comment
) {}
