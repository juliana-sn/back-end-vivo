package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.onboarding.OnboardingToCreateDTO;
import br.purpletech.vivo.dtos.report.ReportDTO;
import br.purpletech.vivo.dtos.report.ReportToCreateDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.exceptions.custom.step.OrderStepAlreadyUsedException;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.ReportRepository;
import br.purpletech.vivo.repositories.StepRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.OnboardingService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import br.purpletech.vivo.exceptions.custom.onboarding.*;
import br.purpletech.vivo.exceptions.custom.user.UserNotFoundException;
import br.purpletech.vivo.exceptions.custom.step.StepNotFoundException;
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
    public OnboardingDTO getById(Long id) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);
        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Transactional
    @Override
    public void deleteOnboarding(Long id) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

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
    }


    @Transactional
    @Override
    public OnboardingDTO updateOnboarding(Long id, OnboardingToCreateDTO updatedOnboarding) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        onboarding.setDt_begin(updatedOnboarding.dt_begin());
        onboarding.setDt_end(updatedOnboarding.dt_end());
        onboarding.setActive(updatedOnboarding.active());

        Onboarding saved = onboardingRepository.save(onboarding);
        return EntityDtoConverter.toOnboardingDTO(saved);
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
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        User user = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        user.getOnboarding().add(onboarding);

        switch (user.getRole()) {
            case MANAGER -> onboarding.setManager(user);
            case BUDDY -> onboarding.setBuddy(user);
            default -> onboarding.setCollaborator(user);
        }

        Onboarding saved = onboardingRepository.save(onboarding);
        return EntityDtoConverter.toOnboardingDTO(saved);
    }

    @Transactional
    @Override
    public void deleteUser(Long id, Long userId) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        switch (user.getRole()) {
            case MANAGER -> onboarding.setManager(null);
            case BUDDY -> onboarding.setBuddy(null);
            default -> onboarding.setCollaborator(null);
        }

        user.getOnboarding().remove(onboarding);
        onboardingRepository.save(onboarding);
        userRepository.save(user);
    }


    @Override
    public OnboardingDTO createChats(Long onboardingId) {
        Onboarding onboarding = onboardingRepository.findById(onboardingId)
                .orElseThrow(OnboardingNotFoundException::new);

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
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        validateStepOrder(onboarding, stepToCreate.stepOrder());

        Step step = EntityDtoConverter.toStep(stepToCreate);
        step.setOnboarding(onboarding);

        Step savedStep = stepRepository.save(step);
        onboarding.getSteps().add(savedStep);

        Onboarding saved = onboardingRepository.save(onboarding);
        return EntityDtoConverter.toOnboardingDTO(saved);
    }


    @Transactional
    @Override
    public void deleteStep(Long id, Long idStep) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        Step step = stepRepository.findById(idStep)
                .orElseThrow(StepNotFoundException::new);

        onboarding.getSteps().remove(step);
        step.setOnboarding(null);

        onboardingRepository.save(onboarding);
        stepRepository.save(step);
    }

    @Transactional
    @Override
    public OnboardingDTO addReport(Long id, ReportToCreateDTO reportToCreate) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        Report report = EntityDtoConverter.toReport(reportToCreate);
        report.setOnboarding(onboarding);
        report.setCollaborator(onboarding.getCollaborator());

        reportRepository.save(report);
        onboarding.getReports().add(report);

        Onboarding saved = onboardingRepository.save(onboarding);
        return EntityDtoConverter.toOnboardingDTO(saved);
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
        Onboarding onboarding = Optional.ofNullable(onboardingRepository.findManagerByCollaboratorId(collaboratorId))
                .orElseThrow(OnboardingNotFoundException::new);

        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Override
    public OnboardingDTO findBuddyByCollaboratorId(Long collaboratorId) {
        Onboarding onboarding = Optional.ofNullable(onboardingRepository.findBuddyByCollaboratorId(collaboratorId))
                .orElseThrow(OnboardingNotFoundException::new);

        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Transactional
    public OnboardingDTO getNextStep(Long onboardingId) {
        Onboarding onboarding = onboardingRepository.findById(onboardingId)
                .orElseThrow(OnboardingNotFoundException::new);

        List<Step> steps = onboarding.getSteps().stream()
                .sorted(Comparator.comparing(Step::getOrder))
                .toList();

        if (steps.isEmpty()) {
            onboarding.setCurrentStep(null);
        } else if (onboarding.getCurrentStep() == null) {
            onboarding.setCurrentStep(steps.get(0)); // começa na primeira etapa
        } else {
            for (int i = 0; i < steps.size() - 1; i++) {
                if (steps.get(i).getId().equals(onboarding.getCurrentStep().getId())) {
                    onboarding.setCurrentStep(steps.get(i + 1)); // próxima etapa
                    break;
                }
            }
            // Se já estiver na última etapa, fica null
            if (steps.get(steps.size() - 1).getId().equals(onboarding.getCurrentStep().getId())) {
                onboarding.setCurrentStep(null);
            }
        }

        onboardingRepository.save(onboarding);
        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }


    private void validateStepOrder(Onboarding onboarding, Integer order) {
        boolean exists = onboarding.getSteps().stream()
                .anyMatch(step -> step.getOrder().equals(order));

        if (exists) {
            throw new OrderStepAlreadyUsedException();
        }
    }
}