package br.purpletech.vivo.config;

import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.TeamRepository;
import br.purpletech.vivo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FirstUserInitializer implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public FirstUserInitializer(TeamRepository teamRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
    }
}
