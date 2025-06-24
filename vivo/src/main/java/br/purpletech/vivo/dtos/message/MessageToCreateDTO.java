package br.purpletech.vivo.dtos.message;

import jakarta.validation.constraints.NotBlank;

public record MessageToCreateDTO(
        @NotBlank(message = "A mensagem não pode estar vazia")
        String content
) {}
