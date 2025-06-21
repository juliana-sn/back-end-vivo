package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.ReportRepository;
import br.purpletech.vivo.repositories.StepRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.OnboardingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnboardingServiceImp implements OnboardingService {
    private final OnboardingRepository onboardingRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StepRepository stepRepository;

    public OnboardingServiceImp(OnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

    @Override
    public Onboarding createOnboarding(Onboarding onboardingToCreate) {
        return onboardingRepository.save(onboardingToCreate);
    }

    @Override
    public List<Onboarding> getAllOnboarding() {
        return onboardingRepository.findAll();
    }

    @Override
    public Optional<Onboarding> getById(Long id) {
        return onboardingRepository.findById(id);
    }

    @Override
    public boolean deleteOnboarding(Long id) {
        return onboardingRepository.findById(id).map(onboarding -> {
            onboardingRepository.delete(onboarding);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<Onboarding> updateOnboarding(Long id, Onboarding updatedOnboarding) {
        return onboardingRepository.findById(id).map(onboarding ->{
            onboarding.setDt_begin(updatedOnboarding.getDt_begin());
            onboarding.setDt_end(updatedOnboarding.getDt_end());
            onboarding.setActive(updatedOnboarding.isActive());
            return onboardingRepository.save(onboarding);
        });
    }

    @Override
    public Optional<List<Onboarding>> findByManagerId(Long userId) {
        return onboardingRepository.findByManagerId(userId);
    }

    @Override
    public Optional<List<Onboarding>> findByBuddyId(Long userId) {
        return onboardingRepository.findByBuddyId(userId);
    }

    @Override
    public Onboarding addUser(Long id, Long id_user) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new RuntimeException("Onboarding não encontrado")));
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id_user).orElseThrow(() -> new RuntimeException("Usuário não encontrado")));

        if(onboardingOptional.isPresent() && userOptional.isPresent()) {
            Onboarding onboarding = onboardingOptional.get();
            User user = userOptional.get();

            user.getOnboarding().add(onboarding);
            if(user.getRole() == Role.MANAGER){
                onboarding.setManager(user);
            }else if (user.getRole() == Role.BUDDY){
                onboarding.setBuddy(user);
            }else{
                onboarding.setCollaborator(user);
            }

            return onboardingRepository.save(onboarding);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteUser(Long id, Long id_user) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Onboarding não encontrado")));

        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id_user).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado")));

        if (onboardingOptional.isPresent() && userOptional.isPresent()){
            Onboarding onboarding = onboardingOptional.get();
            User user = userOptional.get();

            if(user.getRole() == Role.MANAGER){
                onboarding.setManager(null);
                user.getOnboarding().remove(onboarding);
            }else if (user.getRole() == Role.BUDDY){
                onboarding.setBuddy(null);
                user.getOnboarding().remove(onboarding);
            }else{
                onboarding.setCollaborator(null);
                user.getOnboarding().remove(onboarding);
            }

            onboardingRepository.save(onboarding);
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Onboarding addStep(Long id, Step step) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new RuntimeException("Onboarding não encontrado")));
        if(onboardingOptional.isPresent()) {
            Onboarding onboarding = onboardingOptional.get();

            Step savedStep = stepRepository.save(step);

            savedStep.setOnboarding(onboarding);
            stepRepository.save(savedStep);
            onboarding.getSteps().add(step);

            return onboardingRepository.save(onboarding);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteStep(Long id, Long id_step) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Onboarding não encontrado")));

        Optional<Step> stepOptional = Optional.ofNullable(stepRepository.findById(id_step).orElseThrow(()-> new EntityNotFoundException("Etapa não encontrada")));

        if (onboardingOptional.isPresent() && stepOptional.isPresent()){
            Onboarding onboarding = onboardingOptional.get();
            Step step = stepOptional.get();

            onboarding.getSteps().remove(step);
            step.setOnboarding(null);

            onboardingRepository.save(onboarding);
            stepRepository.save(step);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Onboarding addReport(Long id, Report report) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Onboarding não encontrado")));

        if (onboardingOptional.isPresent()){
            Onboarding onboarding = onboardingOptional.get();
            report.setOnboarding(onboarding);
            reportRepository.save(report);
            onboarding.getReports().add(report);
            return onboardingRepository.save(onboarding);
        }else {
            return null;
        }
    }

    @Override
    public Optional<List<Report>> getReports(Long id) {
        return reportRepository.findAllByOnboardingId(id);
    }

    @Override
    public Onboarding findManagerByCollaboratorId(Long collaboratorId) {
        return onboardingRepository.findManagerByCollaboratorId(collaboratorId);
    }

    @Override
    public Onboarding findBuddyByCollaboratorId(Long collaboratorId) {
        return onboardingRepository.findBuddyByCollaboratorId(collaboratorId);
    }

    /*
    @Override
    public Optional<Long> findCollaboratorIdByManagerId(Long userId) {
        return onboardingRepository.findCollaboratorIdByManagerId(userId);
    }

    @Override
    public Optional<Long> findCollaboratorIdByBuddyId(Long userId) {
        return onboardingRepository.findCollaboratorIdByBuddyId(userId);
    }*/

}
