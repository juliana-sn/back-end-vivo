package br.purpletech.vivo.controllers;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.onboarding.OnboardingToCreateDTO;
import br.purpletech.vivo.dtos.report.ReportDTO;
import br.purpletech.vivo.dtos.report.ReportToCreateDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Report;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.services.imp.OnboardingServiceImp;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/onboardings")
public class OnboardingController {
    private final OnboardingServiceImp onboardingServiceImp;

    public OnboardingController(OnboardingServiceImp onboardingServiceImp) {
        this.onboardingServiceImp = onboardingServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<OnboardingDTO>> getAllOnboardings(){
        var onboardings = onboardingServiceImp.getAllOnboarding();
        return ResponseEntity.ok(onboardings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnboardingDTO> getOnboardingById(@PathVariable Long id){
        var onboarding = onboardingServiceImp.getById(id);
        return ResponseEntity.ok(onboarding);
    }

    @PostMapping
    public ResponseEntity<OnboardingDTO> createOnboarding (@RequestBody @Valid OnboardingToCreateDTO onboardingToCreate){
        var onboarding = onboardingServiceImp.createOnboarding(onboardingToCreate);
        return ResponseEntity.ok(onboarding);
    }

    @GetMapping("/manager/{idManager}")
    public ResponseEntity<Optional<List<OnboardingDTO>>> getOnboardingByManagerId(@PathVariable Long idManager){
        var onboardings = onboardingServiceImp.findByManagerId(idManager);
        return ResponseEntity.ok(onboardings);
    }

    @GetMapping("/buddy/{idBuddy}")
    public ResponseEntity<Optional<List<OnboardingDTO>>> getOnboardingByBuddyId(@PathVariable Long idBuddy){
        var onboardings = onboardingServiceImp.findByBuddyId(idBuddy);
        return ResponseEntity.ok(onboardings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnboarding(@PathVariable Long id){
        onboardingServiceImp.deleteOnboarding(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OnboardingDTO> updateOnboarding (@PathVariable Long id, @RequestBody @Valid OnboardingToCreateDTO updatedOnboarding){
        var onboarding = onboardingServiceImp.updateOnboarding(id, updatedOnboarding);
        return ResponseEntity.ok(onboarding);
    }

    @PostMapping("/{id}/users/{idUser}")
    public ResponseEntity<OnboardingDTO> addUserOnboarding(@PathVariable Long id, @PathVariable Long idUser){
        var onboarding = onboardingServiceImp.addUser(id, idUser);
        return ResponseEntity.ok(onboarding);
    }

    @PostMapping("/{id}/chat")
    public ResponseEntity<OnboardingDTO> createChats (@PathVariable Long id){
        var onboarding = onboardingServiceImp.createChats(id);
        return ResponseEntity.ok(onboarding);
    }

    @DeleteMapping("/{id}/users/{idUser}")
    public ResponseEntity<Void> deleteUserOnboarding(@PathVariable Long id, @PathVariable Long idUser){
        onboardingServiceImp.deleteUser(id, idUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/steps")
    public ResponseEntity<OnboardingDTO> addStepOnboarding(@PathVariable Long id, @RequestBody @Valid StepToCreateDTO stepToCreate){
        var onboarding = onboardingServiceImp.addStep(id, stepToCreate);
        return ResponseEntity.ok(onboarding);
    }

    @DeleteMapping("/{id}/steps/{idStep}")
    public ResponseEntity<Void> deleteStepOnboarding(@PathVariable Long id, @PathVariable Long idStep){
        onboardingServiceImp.deleteStep(id, idStep);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/next-step")
    public ResponseEntity<OnboardingDTO> advanceToNextStep(@PathVariable Long id) {
        OnboardingDTO updatedOnboarding = onboardingServiceImp.getNextStep(id);
        return ResponseEntity.ok(updatedOnboarding);
    }

    @PostMapping("/{id}/reports")
    public ResponseEntity<OnboardingDTO> createReport (@PathVariable Long id, @RequestBody @Valid ReportToCreateDTO report){
        var onboarding = onboardingServiceImp.addReport(id, report);
        return ResponseEntity.ok(onboarding);
    }

    @GetMapping("/{id}/reports")
    public ResponseEntity<Optional<List<ReportDTO>>> getAllReports (@PathVariable Long id){
        var reports = onboardingServiceImp.getReports(id);
        return ResponseEntity.ok(reports);
    }
}