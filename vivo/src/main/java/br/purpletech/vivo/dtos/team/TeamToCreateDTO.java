package br.purpletech.vivo.dtos.team;

import jakarta.validation.constraints.NotBlank;

public record TeamToCreateDTO(
        @NotBlank(message = "O nome do time é obrigatório")
        String name,

        @NotBlank(message = "O nome do departamento é obrigatório")
        String department
) {}
