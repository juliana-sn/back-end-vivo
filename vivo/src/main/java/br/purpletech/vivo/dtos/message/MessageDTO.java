package br.purpletech.vivo.dtos.message;

import java.time.LocalDateTime;

public record MessageDTO(
        Long id,
        String content,
        LocalDateTime time,
        String senderName
) {}
