package br.purpletech.vivo.config;

import br.purpletech.vivo.models.Onboarding;
import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
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
    private final PasswordEncoder passwordEncoder;

    public DataLoader(TeamRepository teamRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
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
        user.setName("Paula");
        user.setLastName("Souza");
        user.setTeam(back);
        user.setEmail("paula@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setPosition("Desenvolvedora senior");
        user.setRole(Role.MANAGER);
        user.setTelephone("9999999");

        back.getUsers().add(manager);
        userRepository.save(manager);
        teamRepository.save(back);

        User buddy = new User();
        user.setName("Henrique");
        user.setLastName("Dias");
        user.setTeam(back);
        user.setEmail("henrique@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setPosition("Desenvolvedor pleno");
        user.setRole(Role.BUDDY);
        user.setTelephone("9999999");

        back.getUsers().add(buddy);
        userRepository.save(buddy);
        teamRepository.save(back);

        User collaborator = new User();
        user.setName("Caio");
        user.setLastName("Fonseca");
        user.setTeam(back);
        user.setEmail("caio@email.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setPosition("Desenvolvedor junior");
        user.setRole(Role.COLLABORATOR);
        user.setTelephone("9999999");

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
    }
}
