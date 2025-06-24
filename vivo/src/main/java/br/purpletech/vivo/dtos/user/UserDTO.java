package br.purpletech.vivo.dtos.user;

import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Report;
import br.purpletech.vivo.models.Role;

import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String lastName,
        String email,
        String position,
        String telephone,
        Role role,
        String teamName,
        List<Onboarding> onboardings,
        List<Report> reports
) {}
