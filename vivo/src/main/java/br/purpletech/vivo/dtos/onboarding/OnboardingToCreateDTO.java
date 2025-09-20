package br.purpletech.vivo.dtos.onboarding;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OnboardingToCreateDTO(
        @NotNull(message = "Data de início é obrigatória")
        LocalDate dt_begin,

        @NotNull(message = "Data de término é obrigatória")
        LocalDate dt_end,

        boolean active
) {}
