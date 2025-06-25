package br.purpletech.vivo.dtos.user;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.report.ReportDTO;
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
        List<Long> onboardingIds
) {}
