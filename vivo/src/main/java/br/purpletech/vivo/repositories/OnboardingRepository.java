package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {

    @Query("SELECT o FROM onboardings o JOIN o.users u WHERE u.id = :userId AND u.role = 'MANAGER'")
    List<Onboarding> findOnboardingsByManagerId(@Param("userId") Long userId);

    @Query("SELECT o FROM onboardings o JOIN o.users u WHERE u.id = :userId AND u.role = 'BUDDY'")
    List<Onboarding> findOnboardingsByBuddyId(@Param("userId") Long userId);
}
