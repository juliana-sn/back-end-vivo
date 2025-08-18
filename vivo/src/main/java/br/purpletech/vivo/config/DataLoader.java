package br.purpletech.vivo.config;

import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.repositories.TeamRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.imp.OnboardingServiceImp;
import br.purpletech.vivo.services.imp.StepServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final OnboardingRepository onboardingRepository;
    private final PasswordEncoder passwordEncoder;
    private final OnboardingServiceImp onboardingService;
    private final PlatformRepository platformRepository;
    private final StepServiceImp stepService;

    public DataLoader(TeamRepository teamRepository, UserRepository userRepository, OnboardingRepository onboardingRepository, PasswordEncoder passwordEncoder, OnboardingServiceImp onboardingService, PlatformRepository platformRepository, StepServiceImp stepService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.onboardingRepository = onboardingRepository;
        this.passwordEncoder = passwordEncoder;
        this.onboardingService = onboardingService;
        this.platformRepository = platformRepository;
        this.stepService = stepService;
    }

    @Override
    public void run(String... args) throws Exception {
        Team rh = new Team();
        rh.setName("Equipe 1 RH");
        rh.setDepartment("RH");
        teamRepository.save(rh);

        User user = new User();
        user.setName("Lucia");
        user.setLastName("Santos");
        user.setTeam(rh);
        user.setEmail("lucia@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setPosition("Chefe de departamento pessoal");
        user.setRole(Role.HR);
        user.setTelephone("9999999");

        rh.getUsers().add(user);
        userRepository.save(user);
        teamRepository.save(rh);

        Team back = new Team();
        back.setName("Equipe de Desenvolvimento BackEnd");
        back.setDepartment("Tecnologia");
        teamRepository.save(back);

        User manager = new User();
        manager.setName("Paula");
        manager.setLastName("Souza");
        manager.setTeam(back);
        manager.setEmail("paula@email.com");
        manager.setPassword(passwordEncoder.encode("123456"));
        manager.setPosition("Desenvolvedora senior");
        manager.setRole(Role.MANAGER);
        manager.setTelephone("9999999");

        back.getUsers().add(manager);
        userRepository.save(manager);
        teamRepository.save(back);

        User buddy = new User();
        buddy.setName("Henrique");
        buddy.setLastName("Dias");
        buddy.setTeam(back);
        buddy.setEmail("henrique@email.com");
        buddy.setPassword(passwordEncoder.encode("123456"));
        buddy.setPosition("Desenvolvedor pleno");
        buddy.setRole(Role.BUDDY);
        buddy.setTelephone("9999999");

        back.getUsers().add(buddy);
        userRepository.save(buddy);
        teamRepository.save(back);

        User collaborator = new User();
        collaborator.setName("Caio");
        collaborator.setLastName("Fonseca");
        collaborator.setTeam(back);
        collaborator.setEmail("caio@email.com");
        collaborator.setPassword(passwordEncoder.encode("123456"));
        collaborator.setPosition("Desenvolvedor junior");
        collaborator.setRole(Role.COLLABORATOR);
        collaborator.setTelephone("9999999");

        back.getUsers().add(collaborator);
        userRepository.save(collaborator);
        teamRepository.save(back);

        Onboarding onboarding = new Onboarding();
        onboarding.setDt_begin(LocalDate.now());
        onboarding.setDt_end(LocalDate.now().plusDays(90));
        onboarding.setActive(true);
        onboarding.setManager(manager);
        onboarding.setBuddy(buddy);
        onboarding.setCollaborator(collaborator);

        onboardingRepository.save(onboarding);

        manager.getOnboarding().add(onboarding);
        buddy.getOnboarding().add(onboarding);
        collaborator.getOnboarding().add(onboarding);

        userRepository.save(manager);
        userRepository.save(buddy);
        userRepository.save(collaborator);

        StepToCreateDTO step = new StepToCreateDTO("Etapa 1 - Conhecendo a Vivo", "Série de cursos sobre a história, valores e mercado da Vivo", 1);
        onboardingService.addStep(1L, step);

        TaskToCreateDTO task = new TaskToCreateDTO("Curso: Valores", false);
        stepService.addTask(1L, task);

        TaskToCreateDTO task2 = new TaskToCreateDTO("Curso: História", false);
        stepService.addTask(1L, task2);

        TaskToCreateDTO task3 = new TaskToCreateDTO("Curso: Missão", false);
        stepService.addTask(1L, task3);

        TaskToCreateDTO task4 = new TaskToCreateDTO("Palestra sobre o mercado", false);
        stepService.addTask(1L, task4);

        StepToCreateDTO step2 = new StepToCreateDTO("Etapa 2 - Cursos obrigatórios de Tecnologia", "Cursos e atividades sobre informática e cybersegurança básica", 2);
        onboardingService.addStep(1L, step2);

        TaskToCreateDTO task5 = new TaskToCreateDTO("Curso: LGPD", false);
        stepService.addTask(2L, task4);

        TaskToCreateDTO task6 = new TaskToCreateDTO("Curso: Introdução a Plataformas", false);
        stepService.addTask(2L, task4);

        Platform platform = new Platform();
        platform.setName("Azure DevOps");
        platform.setUrl("exemplo.com");
        platform.setType_access("Fale com seu gestor");
        platformRepository.save(platform);

        if (!back.getPlatforms().contains(platform)) {
            back.getPlatforms().add(platform);
        }

        Platform platform2 = new Platform();
        platform2.setName("Vivo Access");
        platform2.setUrl("exemplo.com");
        platform2.setType_access("Acesso já liberado");
        platformRepository.save(platform2);

        if (!back.getPlatforms().contains(platform2)) {
            back.getPlatforms().add(platform2);
        }

        teamRepository.save(back);


        Platform platform3 = new Platform();
        platform3.setName("GOVAPP");
        platform3.setUrl("exemplo.com");
        platform3.setType_access("Acesse o site da plataforma");
        platformRepository.save(platform3);

        Platform platform4 = new Platform();
        platform4.setName("GOVAPI");
        platform4.setUrl("exemplo.com");
        platform4.setType_access("Acesso já liberado");
        platformRepository.save(platform4);

    }
}