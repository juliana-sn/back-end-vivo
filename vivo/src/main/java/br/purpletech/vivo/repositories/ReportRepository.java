package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<List<Report>> findAllByOnboardingId (Long onboardingId);
}
