package br.purpletech.vivo.dtos.team;

import br.purpletech.vivo.dtos.platform.PlatformDTO;
import br.purpletech.vivo.dtos.user.UserDTO;

import java.util.List;

public record TeamDTO(
        Long id,
        String name,
        String department,
        List<Long> platformIds,
        List<UserDTO> users
) {}
