package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface OnboardingService {
    Onboarding createOnboarding(Onboarding onboardingToCreate);
    List<Onboarding> getAllOnboarding();
    Optional<Onboarding> getById(Long id);
    boolean deleteOnboarding(Long id);
    Optional<Onboarding> updateOnboarding(Long id, Onboarding updatedOnboarding);
    Optional<List<Onboarding>> findByManagerId(Long userId);
    Optional<List<Onboarding>> findByBuddyId(Long userId);

    Onboarding addUser(Long id, Long id_user);
    boolean deleteUser(Long id, Long id_user);

    Onboarding addStep(Long id, Step step);
    boolean deleteStep(Long id, Long id_step);

}
