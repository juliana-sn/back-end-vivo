package br.purpletech.vivo.dtos.platform;

import jakarta.validation.constraints.NotBlank;

public record PlatformToCreateDTO(
        @NotBlank(message = "Nome da plataforma é obrigatório")
        String name,

        @NotBlank(message = "Tipo de acesso é obrigatório")
        String type_access,

        @NotBlank(message = "URL é obrigatória")
        String url
) {}
