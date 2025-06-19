package br.purpletech.vivo.controllers;

import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Step;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.services.imp.OnboardingServiceImp;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Onboarding>> getAllOnboardings(){
        var onboardings = onboardingServiceImp.getAllOnboarding();
        return ResponseEntity.ok(onboardings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Onboarding>> getOnboardingById(@PathVariable Long id){
        var onboarding = onboardingServiceImp.getById(id);
        return ResponseEntity.ok(onboarding);
    }

    @PostMapping
    public ResponseEntity<Onboarding> createOnboarding (@RequestBody Onboarding onboardingToCreate){
        var onboarding = onboardingServiceImp.createOnboarding(onboardingToCreate);
        return ResponseEntity.ok(onboarding);
    }

    @GetMapping("/manager/{id_manager}")
    public ResponseEntity<Optional<List<Onboarding>>> getOnboardingByManagerId(@PathVariable Long id_manager){
        var onboardings = onboardingServiceImp.findByManagerId(id_manager);
        return ResponseEntity.ok(onboardings);
    }

    @GetMapping("/buddy/{id_buddy}")
    public ResponseEntity<Optional<List<Onboarding>>> getOnboardingByBuddyId(@PathVariable Long id_buddy){
        var onboardings = onboardingServiceImp.findByBuddyId(id_buddy);
        return ResponseEntity.ok(onboardings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnboarding(@PathVariable Long id){
        onboardingServiceImp.deleteOnboarding(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Onboarding>> updateOnboarding (@PathVariable Long id, @RequestBody Onboarding updatedOnboarding){
        var onboarding = onboardingServiceImp.updateOnboarding(id, updatedOnboarding);
        return ResponseEntity.ok(onboarding);
    }

    @PostMapping("/{id}/users/{id_user}")
    public ResponseEntity<Onboarding> addUserOnboarding(@PathVariable Long id, @PathVariable Long id_user){
        var onboarding = onboardingServiceImp.addUser(id, id_user);
        return ResponseEntity.ok(onboarding);
    }

    @DeleteMapping("/{id}/users/{id_user}")
    public ResponseEntity<Onboarding> deleteUserOnboarding(@PathVariable Long id, @PathVariable Long id_user){
        onboardingServiceImp.deleteUser(id, id_user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/steps")
    public ResponseEntity<Onboarding> addStepOnboarding(@PathVariable Long id, @RequestBody Step stepToCreate){
        var onboarding = onboardingServiceImp.addStep(id, stepToCreate);
        return ResponseEntity.ok(onboarding);
    }

    @DeleteMapping("/{id}/steps/{id_step}")
    public ResponseEntity<Onboarding> deleteStepOnboarding(@PathVariable Long id, @PathVariable Long id_step){
        onboardingServiceImp.deleteStep(id, id_step);
        return ResponseEntity.noContent().build();
    }
}
