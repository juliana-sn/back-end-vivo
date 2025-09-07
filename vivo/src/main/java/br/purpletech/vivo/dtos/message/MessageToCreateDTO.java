package br.purpletech.vivo.dtos.message;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record MessageToCreateDTO(
        @NotBlank(message = "A mensagem não pode estar vazia")
        String content
) {}
