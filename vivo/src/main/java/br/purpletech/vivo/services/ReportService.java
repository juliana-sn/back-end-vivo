package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report createReport (Report reportToCreate);
    Optional<List<Report>> findAllByOnboardingId (Long onboardingId);
}
