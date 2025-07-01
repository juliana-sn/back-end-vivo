package br.purpletech.vivo.config;

import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.OnboardingRepository;
import br.purpletech.vivo.repositories.TeamRepository;
import br.purpletech.vivo.repositories.UserRepository;
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

    public DataLoader(TeamRepository teamRepository, UserRepository userRepository, OnboardingRepository onboardingRepository, PasswordEncoder passwordEncoder) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.onboardingRepository = onboardingRepository;
        this.passwordEncoder = passwordEncoder;
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

        manager.getOnboarding().add(onboarding);
        buddy.getOnboarding().add(onboarding);
        collaborator.getOnboarding().add(onboarding);
        userRepository.save(collaborator);
        userRepository.save(buddy);
        userRepository.save(manager);
        onboardingRepository.save(onboarding);
    }
}