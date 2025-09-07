package br.purpletech.vivo.config;

import br.purpletech.vivo.dtos.message.MessageToCreateDTO;
import br.purpletech.vivo.dtos.report.ReportToCreateDTO;
import br.purpletech.vivo.dtos.step.StepToCreateDTO;
import br.purpletech.vivo.dtos.task.TaskToCreateDTO;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.repositories.TeamRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.imp.ChatServiceImp;
import br.purpletech.vivo.services.imp.OnboardingServiceImp;
import br.purpletech.vivo.services.imp.StepServiceImp;
import br.purpletech.vivo.services.imp.TaskServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final OnboardingRepository onboardingRepository;
    private final PasswordEncoder passwordEncoder;
    private final OnboardingServiceImp onboardingService;
    private final PlatformRepository platformRepository;
    private final StepServiceImp stepService;
    private final TaskServiceImp taskService;
    private final ChatServiceImp chatService;

    public DataLoader(TeamRepository teamRepository, UserRepository userRepository, OnboardingRepository onboardingRepository, PasswordEncoder passwordEncoder, OnboardingServiceImp onboardingService, PlatformRepository platformRepository, StepServiceImp stepService, TaskServiceImp taskService, ChatServiceImp chatService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.onboardingRepository = onboardingRepository;
        this.passwordEncoder = passwordEncoder;
        this.onboardingService = onboardingService;
        this.platformRepository = platformRepository;
        this.stepService = stepService;
        this.taskService = taskService;
        this.chatService = chatService;
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
        user.setEmail("lucia@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setPosition("Chefe de departamento pessoal");
        user.setRole(Role.HR);
        user.setTelephone("9999999");
        user.setTeam(rh);
        userRepository.save(user);

        Team back = new Team();
        back.setName("Equipe de Desenvolvimento BackEnd");
        back.setDepartment("Tecnologia");
        teamRepository.save(back);

        User manager = new User();
        manager.setName("Paula");
        manager.setLastName("Souza");
        manager.setEmail("paula@email.com");
        manager.setPassword(passwordEncoder.encode("123456"));
        manager.setPosition("Desenvolvedora senior");
        manager.setRole(Role.MANAGER);
        manager.setTelephone("9999999");
        manager.setTeam(back);

        User buddy = new User();
        buddy.setName("Henrique");
        buddy.setLastName("Dias");
        buddy.setEmail("henrique@email.com");
        buddy.setPassword(passwordEncoder.encode("123456"));
        buddy.setPosition("Desenvolvedor pleno");
        buddy.setRole(Role.BUDDY);
        buddy.setTelephone("9999999");
        buddy.setTeam(back);

        User collaborator = new User();
        collaborator.setName("Caio");
        collaborator.setLastName("Fonseca");
        collaborator.setEmail("caio@email.com");
        collaborator.setPassword(passwordEncoder.encode("123456"));
        collaborator.setPosition("Desenvolvedor junior");
        collaborator.setRole(Role.COLLABORATOR);
        collaborator.setTelephone("9999999");
        collaborator.setTeam(back);

        userRepository.saveAll(List.of(manager, buddy, collaborator));

        Onboarding onboarding = new Onboarding();
        onboarding.setDt_begin(LocalDate.now());
        onboarding.setDt_end(LocalDate.now().plusDays(90));
        onboarding.setActive(true);
        onboarding.getUsers().addAll(List.of(manager, buddy, collaborator));
        onboardingRepository.save(onboarding);

        onboardingService.createChats(onboarding.getId());

        chatService.sendMessage(collaborator.getId(), manager.getId(), new MessageToCreateDTO("Olá Paula, agradeço pela recepção e pela oportunidade. Estou motivado para contribuir e aprender. Se possível, gostaria de alinhar expectativas e receber materiais que apoiem minha adaptação. Fico à disposição!"));
        chatService.sendMessage(collaborator.getId(), buddy.getId(), new MessageToCreateDTO("Olá Henrique, obrigado pelo apoio nesse início. Se tiver dicas ou materiais que ajudem a entender melhor os processos da equipe, agradeço muito. Estou à disposição para colaborar no que for preciso."));
        chatService.sendMessage(manager.getId(), collaborator.getId(), new MessageToCreateDTO("Olá Caio, fico feliz com sua iniciativa. Vamos agendar um bate-papo para alinharmos expectativas. Te envio materiais de apoio ainda hoje. Conte comigo!"));
        chatService.sendMessage(buddy.getId(), collaborator.getId(), new MessageToCreateDTO("Oi Caio, que bom que está se adaptando bem! Vou te encaminhar alguns documentos úteis e podemos marcar uma call para conversar melhor. Seja bem-vindo!"));

        onboardingService.addStep(onboarding.getId(), new StepToCreateDTO("Etapa 1 - Conhecendo a Vivo", "Série de cursos sobre a história, valores e mercado da Vivo", false, 1));
        stepService.addTask(1L, new TaskToCreateDTO("Curso: Valores", false));
        stepService.addTask(1L, new TaskToCreateDTO("Curso: História", false));
        stepService.addTask(1L, new TaskToCreateDTO("Curso: Missão", false));
        stepService.addTask(1L, new TaskToCreateDTO("Palestra sobre o mercado", false));

        onboardingService.addStep(onboarding.getId(), new StepToCreateDTO("Etapa 2 - Cursos obrigatórios de Tecnologia", "Cursos e atividades sobre informática e cybersegurança básica", false, 2));
        stepService.addTask(2L, new TaskToCreateDTO("Curso: LGPD", false));
        stepService.addTask(2L, new TaskToCreateDTO("Curso: Introdução a Plataformas", false));

        TaskToCreateDTO taskStandard1 = new TaskToCreateDTO("Curso: Valores", true);
        TaskToCreateDTO taskStandard2 = new TaskToCreateDTO("Curso: Introdução a Plataformas", true);
        TaskToCreateDTO taskStandard3 = new TaskToCreateDTO("Curso: História", true);
        TaskToCreateDTO taskStandard4 = new TaskToCreateDTO("Curso: Missão", true);

        taskService.createTask(taskStandard1);
        taskService.createTask(taskStandard2);
        taskService.createTask(taskStandard3);
        taskService.createTask(taskStandard4);

        Platform platform1 = new Platform();
        platform1.setName("Azure DevOps");
        platform1.setUrl("exemplo.com");
        platform1.setType_access("Fale com seu gestor");

        Platform platform2 = new Platform();
        platform2.setName("Vivo Access");
        platform2.setUrl("exemplo.com");
        platform2.setType_access("Acesso já liberado");

        Platform platform3 = new Platform();
        platform3.setName("GOVAPP");
        platform3.setUrl("exemplo.com");
        platform3.setType_access("Acesse o site da plataforma");

        Platform platform4 = new Platform();
        platform4.setName("GOVAPI");
        platform4.setUrl("exemplo.com");
        platform4.setType_access("Acesso já liberado");

        platformRepository.saveAll(List.of(platform1, platform2, platform3, platform4));

        back.getPlatforms().addAll(List.of(platform1, platform2));
        teamRepository.save(back);

        onboardingService.addReport(onboarding.getId(), new ReportToCreateDTO(4, "Tive uma dúvida sobre como acessar os relatórios da equipe, mas consegui resolver com ajuda do gestor.", "Participei do workshop de integração com o time de produto.", "Foi uma semana produtiva, estou me adaptando bem!"));
    }

}