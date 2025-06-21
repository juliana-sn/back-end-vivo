package br.purpletech.vivo.controllers;

import br.purpletech.vivo.models.Report;
import br.purpletech.vivo.services.imp.ReportServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportServiceImp reportServiceImp;

    public ReportController(ReportServiceImp reportServiceImp) {
        this.reportServiceImp = reportServiceImp;
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report reportToCreate){
        var report = reportServiceImp.createReport(reportToCreate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{id_onboarding}")
    public ResponseEntity<Optional<List<Report>>> getAllReportsByOnboardingId (@PathVariable Long id_onboarding){
        var reports = reportServiceImp.findAllByOnboardingId(id_onboarding);
        return ResponseEntity.ok(reports);
    }
}