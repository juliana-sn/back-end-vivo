package br.purpletech.vivo.dtos.report;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReportToCreateDTO(
        @NotNull(message = "Sentimento é obrigatório")
        @Min(value = 1, message = "Valor mínimo para o sentimento é 1")
        @Max(value = 4, message = "Valor máximo para o sentimento é 4")
        int feeling,

        String question,

        String event,

        String comment
) {}
