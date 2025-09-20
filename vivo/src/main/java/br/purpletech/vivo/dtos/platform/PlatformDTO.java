package br.purpletech.vivo.dtos.platform;

public record PlatformDTO(
        Long id,
        String name,
        String type_access,
        String url
) {}
