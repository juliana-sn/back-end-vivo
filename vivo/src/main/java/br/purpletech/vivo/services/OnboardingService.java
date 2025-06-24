package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.onboarding.OnboardingToCreateDTO;
import br.purpletech.vivo.dtos.report.ReportDTO;
import br.purpletech.vivo.dtos.report.ReportToCreateDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Report;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface OnboardingService {
    OnboardingDTO createOnboarding(OnboardingToCreateDTO onboardingToCreate);
    List<OnboardingDTO> getAllOnboarding();
    Optional<OnboardingDTO> getById(Long id);
    boolean deleteOnboarding(Long id);
    Optional<OnboardingDTO> updateOnboarding(Long id, OnboardingToCreateDTO updatedOnboarding);
    Optional<List<OnboardingDTO>> findByManagerId(Long userId);
    Optional<List<OnboardingDTO>> findByBuddyId(Long userId);

    OnboardingDTO addUser(Long id, Long idUser);
    boolean deleteUser(Long id, Long idUser);

    OnboardingDTO addStep(Long id, StepToCreateDTO step);
    boolean deleteStep(Long id, Long idStep);

    OnboardingDTO addReport(Long id, ReportToCreateDTO report);
    Optional<List<ReportDTO>> getReports (Long id);

    OnboardingDTO findManagerByCollaboratorId(Long collaboratorId);
    OnboardingDTO findBuddyByCollaboratorId(Long collaboratorId);

}
