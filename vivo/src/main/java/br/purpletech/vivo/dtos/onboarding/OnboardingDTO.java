package br.purpletech.vivo.dtos.onboarding;

import br.purpletech.vivo.dtos.report.ReportDTO;
import br.purpletech.vivo.dtos.step.StepDTO;
import br.purpletech.vivo.dtos.user.UserDTO;
import br.purpletech.vivo.models.User;

import java.time.LocalDate;
import java.util.List;

public record OnboardingDTO(
        Long id,
        LocalDate dt_begin,
        LocalDate dt_end,
        boolean active,
        UserDTO manager,
        UserDTO buddy,
        UserDTO collaborator,
        List<StepDTO> steps,
        List<ReportDTO> reports,
        StepDTO currentStep
) {}
