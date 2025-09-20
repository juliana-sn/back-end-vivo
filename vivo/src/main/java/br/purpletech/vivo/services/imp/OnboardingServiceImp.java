package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.onboarding.OnboardingToCreateDTO;
import br.purpletech.vivo.dtos.report.ReportDTO;
import br.purpletech.vivo.dtos.report.ReportToCreateDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.exceptions.custom.step.OrderStepAlreadyUsedException;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.*;
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
    private TaskRepository taskRepository;

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

        for (User user : onboarding.getUsers()) {
            user.getOnboarding().remove(onboarding);
        }

        onboarding.getUsers().clear();

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

    private User getUserByRole(Onboarding onboarding, Role role) {
        return onboarding.getUsers().stream()
                .filter(user -> user.getRole().equals(role))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<List<OnboardingDTO>> findByManagerId(Long userId) {
        List<Onboarding> onboardings = onboardingRepository.findOnboardingsByManagerId(userId);
        return Optional.of(onboardings.stream()
                .map(EntityDtoConverter::toOnboardingDTO)
                .toList());
    }

    @Override
    public Optional<List<OnboardingDTO>> findByBuddyId(Long userId) {
        List<Onboarding> onboardings = onboardingRepository.findOnboardingsByBuddyId(userId);
        return Optional.of(onboardings.stream()
                .map(EntityDtoConverter::toOnboardingDTO)
                .toList());
    }


    @Transactional
    @Override
    public OnboardingDTO addUser(Long id, Long idUser) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        User user = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() == Role.COLLABORATOR) {
            if(!user.getOnboarding().isEmpty()){
                throw new IllegalStateException("Usuário colaborador já está vinculado a um onboarding.");
            }
        }

        user.getOnboarding().add(onboarding);
        onboarding.getUsers().add(user);

        userRepository.save(user);
        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Transactional
    @Override
    public void deleteUser(Long id, Long userId) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        onboarding.getUsers().remove(user);
        user.getOnboarding().remove(onboarding);

        userRepository.save(user);
    }


    @Override
    @Transactional
    public OnboardingDTO createChats(Long onboardingId) {
        Onboarding onboarding = onboardingRepository.findById(onboardingId)
                .orElseThrow(OnboardingNotFoundException::new);

        User collaborator = getUserByRole(onboarding, Role.COLLABORATOR);
        User manager = getUserByRole(onboarding, Role.MANAGER);
        User buddy = getUserByRole(onboarding, Role.BUDDY);

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

        validateStepOrder(onboarding, stepToCreate.orderStep());

        Step step = EntityDtoConverter.toStep(stepToCreate);
        step.setOnboarding(onboarding);

        step.setInProgress(false);

        if (onboarding.getCurrentStep() == null) {
            step.setInProgress(true);
        }

        step.setOnboarding(onboarding);
        stepRepository.save(step);

        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }


    @Transactional
    @Override
    public void deleteStep(Long id, Long idStep) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        Step step = stepRepository.findById(idStep)
                .orElseThrow(StepNotFoundException::new);

        taskRepository.deleteAll(step.getTasks());
        step.setOnboarding(null);
        stepRepository.delete(step);
    }

    @Transactional
    @Override
    public OnboardingDTO addReport(Long id, ReportToCreateDTO reportToCreate) {
        Onboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(OnboardingNotFoundException::new);

        User collaborator = getUserByRole(onboarding, Role.COLLABORATOR);

        if (collaborator == null) {
            throw new UserNotFoundException();
        }

        Report report = EntityDtoConverter.toReport(reportToCreate);
        report.setOnboarding(onboarding);
        report.setCollaborator(collaborator);

        report.setOnboarding(onboarding);
        report.setCollaborator(collaborator);

        reportRepository.save(report);
        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    @Override
    public Optional<List<ReportDTO>> getReports(Long id) {
        return reportRepository.findAllByOnboardingId(id)
                .map(reports -> reports.stream()
                        .map(EntityDtoConverter::toReportDTO)
                        .toList()
                );
    }

    @Transactional
    public OnboardingDTO getNextStep(Long onboardingId) {
        Onboarding onboarding = onboardingRepository.findById(onboardingId)
                .orElseThrow(OnboardingNotFoundException::new);

        List<Step> orderedSteps = onboarding.getSteps().stream()
                .sorted(Comparator.comparing(Step::getStepOrder))
                .toList();

        Step currentStep = onboarding.getCurrentStep();

        if (orderedSteps.isEmpty()) {
            return EntityDtoConverter.toOnboardingDTO(onboarding);
        }

        if (currentStep == null) {
            orderedSteps.get(0).setInProgress(true);
        } else {
            currentStep.setInProgress(false);
            stepRepository.save(currentStep);

            Step nextStep = orderedSteps.stream()
                    .filter(step -> step.getStepOrder() > currentStep.getStepOrder())
                    .findFirst()
                    .orElse(null);

            if (nextStep != null) {
                nextStep.setInProgress(true);
                stepRepository.save(nextStep);
            }
        }

        return EntityDtoConverter.toOnboardingDTO(onboarding);
    }

    private void validateStepOrder(Onboarding onboarding, Integer order) {
        boolean exists = onboarding.getSteps().stream()
                .anyMatch(step -> step.getStepOrder().equals(order));

        if (exists) {
            throw new OrderStepAlreadyUsedException();
        }
    }
}