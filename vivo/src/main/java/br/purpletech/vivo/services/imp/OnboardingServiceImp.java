package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.onboarding.OnboardingToCreateDTO;
import br.purpletech.vivo.dtos.report.ReportDTO;
import br.purpletech.vivo.dtos.report.ReportToCreateDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.ReportRepository;
import br.purpletech.vivo.repositories.StepRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.OnboardingService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OnboardingServiceImp implements OnboardingService {
    private final OnboardingRepository onboardingRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private ChatServiceImp chatServiceImp;

    public OnboardingServiceImp(OnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

    @Transactional
    @Override
    public OnboardingDTO createOnboarding(OnboardingToCreateDTO onboardingToCreate) {
        Onboarding onboarding = EntityDtoConverter.toOnboarding(onboardingToCreate);
        Onboarding onboardingSaved = onboardingRepository.save(onboarding);
        return EntityDtoConverter.toOnboardingDTO(onboardingSaved);
    }

    @Override
    public List<OnboardingDTO> getAllOnboarding() {
        return onboardingRepository.findAll().stream()
                .map(EntityDtoConverter::toOnboardingDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OnboardingDTO> getById(Long id) {
        return onboardingRepository.findById(id).map(EntityDtoConverter::toOnboardingDTO);
    }

    @Transactional
    @Override
    public boolean deleteOnboarding(Long id) {
        return onboardingRepository.findById(id).map(onboarding -> {
            if (onboarding.getManager() != null) {
                onboarding.getManager().getOnboarding().remove(onboarding);
                onboarding.setManager(null);
            }

            if (onboarding.getBuddy() != null) {
                onboarding.getBuddy().getOnboarding().remove(onboarding);
                onboarding.setBuddy(null);
            }

            if (onboarding.getCollaborator() != null) {
                onboarding.getCollaborator().getOnboarding().remove(onboarding);
                onboarding.setCollaborator(null);
            }

            onboardingRepository.save(onboarding);
            onboardingRepository.delete(onboarding);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public Optional<OnboardingDTO> updateOnboarding(Long id, OnboardingToCreateDTO updatedOnboarding) {
        return onboardingRepository.findById(id).map(onboarding ->{
            onboarding.setDt_begin(updatedOnboarding.dt_begin());
            onboarding.setDt_end(updatedOnboarding.dt_end());
            onboarding.setActive(updatedOnboarding.active());
            Onboarding onboardingSaved = onboardingRepository.save(onboarding);
            return EntityDtoConverter.toOnboardingDTO(onboardingSaved);
        });
    }

    @Override
    public Optional<List<OnboardingDTO>> findByManagerId(Long userId) {
        return onboardingRepository.findByManagerId(userId)
                .map(onboardings -> onboardings.stream()
                        .map(EntityDtoConverter::toOnboardingDTO)
                        .toList()
                );
    }

    @Override
    public Optional<List<OnboardingDTO>> findByBuddyId(Long userId) {
        return onboardingRepository.findByBuddyId(userId)
                .map(onboardings -> onboardings.stream()
                        .map(EntityDtoConverter::toOnboardingDTO)
                        .toList()
                );
    }

    @Transactional
    @Override
    public OnboardingDTO addUser(Long id, Long idUser) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new RuntimeException("Onboarding não encontrado")));
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("Usuário não encontrado")));

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

            Onboarding onboardingSaved = onboardingRepository.save(onboarding);
            return EntityDtoConverter.toOnboardingDTO(onboardingSaved);
        }else{
            return null;
        }
    }

    @Transactional
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

    public OnboardingDTO createChats(Long id) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Onboarding não encontrado para o ID " + id));

        User collaborator = onboarding.getCollaborator();
        User manager = onboarding.getManager();
        User buddy = onboarding.getBuddy();

        if (collaborator != null && manager != null) {
            chatServiceImp.findOrCreateChat(collaborator.getId(), manager.getId());
        }

        if (collaborator != null && buddy != null) {
            chatServiceImp.findOrCreateChat(collaborator.getId(), buddy.getId());
        }

        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Transactional
    @Override
    public OnboardingDTO addStep(Long id, StepToCreateDTO stepToCreate) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new RuntimeException("Onboarding não encontrado")));
        if(onboardingOptional.isPresent()) {
            Onboarding onboarding = onboardingOptional.get();
            Step step = EntityDtoConverter.toStep(stepToCreate);
            Step savedStep = stepRepository.save(step);

            savedStep.setOnboarding(onboarding);
            stepRepository.save(savedStep);
            onboarding.getSteps().add(step);

            Onboarding onboardingSaved = onboardingRepository.save(onboarding);
            return EntityDtoConverter.toOnboardingDTO(onboardingSaved);
        }else{
            return null;
        }
    }

    @Transactional
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

    @Transactional
    @Override
    public OnboardingDTO addReport(Long id, ReportToCreateDTO reportToCreate) {
        Optional<Onboarding> onboardingOptional = Optional.ofNullable(onboardingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Onboarding não encontrado")));

        if (onboardingOptional.isPresent()){
            Onboarding onboarding = onboardingOptional.get();
            Report report = EntityDtoConverter.toReport(reportToCreate);
            report.setOnboarding(onboarding);
            report.setCollaborator(onboarding.getCollaborator());
            reportRepository.save(report);
            onboarding.getReports().add(report);
            Onboarding onboardingSaved = onboardingRepository.save(onboarding);
            return EntityDtoConverter.toOnboardingDTO(onboardingSaved);
        }else {
            return null;
        }
    }

    @Override
    public Optional<List<ReportDTO>> getReports(Long id) {
        return reportRepository.findAllByOnboardingId(id)
                .map(reports -> reports.stream()
                        .map(EntityDtoConverter::toReportDTO)
                        .toList()
                );
    }

    @Override
    public OnboardingDTO findManagerByCollaboratorId(Long collaboratorId) {
        Onboarding onboarding = onboardingRepository.findManagerByCollaboratorId(collaboratorId);
        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Override
    public OnboardingDTO findBuddyByCollaboratorId(Long collaboratorId) {
        Onboarding onboarding = onboardingRepository.findBuddyByCollaboratorId(collaboratorId);
        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Transactional
    public OnboardingDTO getNextStep(Long onboardingId) {
        Onboarding onboarding = onboardingRepository.findById(onboardingId)
                .orElseThrow(() -> new EntityNotFoundException("Onboarding não encontrado"));

        List<Step> steps = onboarding.getSteps().stream()
                .sorted(Comparator.comparing(Step::getOrder)) // assumindo que Step tem campo "order"
                .toList();

        if (onboarding.getCurrentStep() == null && !steps.isEmpty()) {
            onboarding.setCurrentStep(steps.get(0));
        } else {
            for (int i = 0; i < steps.size(); i++) {
                if (steps.get(i).getId().equals(onboarding.getCurrentStep().getId())) {
                    if (i + 1 < steps.size()) {
                        onboarding.setCurrentStep(steps.get(i + 1));
                    } else {
                        onboarding.setCurrentStep(null); // acabou as etapas
                    }
                    break;
                }
            }
        }

        onboardingRepository.save(onboarding);

        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }


}