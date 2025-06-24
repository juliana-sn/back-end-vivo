package br.purpletech.vivo.dtos.chat;

import br.purpletech.vivo.dtos.message.MessageDTO;
import br.purpletech.vivo.dtos.user.UserDTO;

import java.util.List;

public record ChatDTO(
        Long id,
        List<UserDTO> participants,
        List<MessageDTO> messages
) {}
