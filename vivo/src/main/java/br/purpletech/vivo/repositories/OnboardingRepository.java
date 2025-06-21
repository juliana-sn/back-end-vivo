package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
    Optional<List<Onboarding>> findByManagerId(Long userId);
    Optional<List<Onboarding>> findByBuddyId(Long userId);

    Onboarding findManagerByCollaboratorId(Long collaboratorId);

    Onboarding findBuddyByCollaboratorId(Long collaboratorId);

    /*
    Optional<Long> findCollaboratorIdByManagerId(Long userId);
    Optional<Long> findCollaboratorIdByBuddyId(Long userId); */
}
