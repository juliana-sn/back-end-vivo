package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.Report;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.ReportRepository;
import br.purpletech.vivo.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImp implements ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    private OnboardingRepository onboardingRepository;

    public ReportServiceImp(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public Report createReport(Report reportToCreate) {
        return reportRepository.save(reportToCreate);
    }

    @Override
    public Optional<List<Report>> findAllByOnboardingId(Long onboardingId) {
    onboardingRepository.findById(onboardingId).orElseThrow(() -> new RuntimeException("Onboarding não encontrado"));
        return reportRepository.findAllByOnboardingId(onboardingId);
    }
}
