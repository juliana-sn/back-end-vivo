package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.auth.AuthRequest;
import br.purpletech.vivo.dtos.auth.AuthResponse;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.exceptions.custom.user.EmailAlreadyUsedException;
import br.purpletech.vivo.exceptions.custom.team.TeamNotFoundException;
import br.purpletech.vivo.exceptions.custom.user.UserNotFoundException;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.TeamRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.utils.EntityDtoConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp {

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.teamRepository = teamRepository;
    }

    public String register(UserToCreateDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        Team team = teamRepository.findById(dto.teamId())
                .orElseThrow(TeamNotFoundException::new);

        User user = EntityDtoConverter.toUser(dto, team);
        user.setPassword(passwordEncoder.encode(dto.password()));

        userRepository.save(user);
        return "Usuário registrado com sucesso!";
    }

    public AuthResponse login(AuthRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userRepository.findByEmail(request.email()).orElseThrow(UserNotFoundException::new);


        String token = jwtService.generateToken(auth.getName(), auth.getAuthorities());
        return new AuthResponse(token, user.getId(), user.getRole().name());
    }
}
