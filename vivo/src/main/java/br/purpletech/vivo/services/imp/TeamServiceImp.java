package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.team.TeamDTO;
import br.purpletech.vivo.dtos.team.TeamToCreateDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.repositories.TeamRepository;

import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.TeamService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImp implements TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlatformRepository platformRepository;

    public TeamServiceImp(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getAllTeams() { return teamRepository.findAll().stream()
            .map(EntityDtoConverter::toTeamDTO)
            .collect(Collectors.toList());}

    public Optional<TeamDTO> getById(Long id) {
        return teamRepository.findById(id).map(EntityDtoConverter::toTeamDTO);
    }

    @Transactional
    public TeamDTO createTeam(TeamToCreateDTO teamToCreate) {
        Team team = EntityDtoConverter.toTeam(teamToCreate);
        Team teamSaved = teamRepository.save(team);
        return EntityDtoConverter.toTeamDTO(teamSaved);
    }

    @Transactional
    @Override
    public Optional<TeamDTO> updateNameTeam(Long id, TeamToCreateDTO updateTeam) {
        return teamRepository.findById(id).map(team -> {
            team.setName(updateTeam.name());
            Team teamSaved = teamRepository.save(team);
            return EntityDtoConverter.toTeamDTO(teamSaved);
        });
    }

    @Transactional
    @Override
    public boolean deleteTeam(Long id) {
        return teamRepository.findById(id).map(team -> {
            teamRepository.delete(team);
            return true;
        }).orElse(false);
    }

    @Transactional
    @Override
    public TeamDTO addUser(Long id, UserToCreateDTO userToCreate) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipe não encontrada")));
        if(teamOptional.isPresent()) {
            Team team = teamOptional.get();
            User user = EntityDtoConverter.toUser(userToCreate);

            user.setTeam(team);
            User userSaved = userRepository.save(user);

            team.getUsers().add(userSaved);
            Team teamSaved = teamRepository.save(team);
            return EntityDtoConverter.toTeamDTO(teamSaved);
        }else{
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteUser(Long idTeam, Long idUser) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(idTeam).orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada")));

        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(idUser).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado")));

        if (teamOptional.isPresent() && userOptional.isPresent()){
            Team team = teamOptional.get();
            User user = userOptional.get();


            team.getUsers().remove(user);
            user.setTeam(null);
            teamRepository.save(team);
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    @Override
    public TeamDTO addPlatform(Long id, Long idPlatform) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipe não encontrada")));
        Optional<Platform> platformOptional = Optional.ofNullable(platformRepository.findById(idPlatform).orElseThrow(() -> new RuntimeException("Plataforma não encontrada")));

        if(teamOptional.isPresent() && platformOptional.isPresent()) {
            Team team = teamOptional.get();
            Platform platform = platformOptional.get();

            team.getPlatforms().add(platform);
            platform.getTeams().add(team);

            platformRepository.save(platform);
            Team teamSaved = teamRepository.save(team);
            return EntityDtoConverter.toTeamDTO(teamSaved);
        }else{
            return null;
        }
    }


    @Transactional
    @Override
    public boolean deletePlatform(Long idTeam, Long idPlatform) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(idTeam).orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada")));

        Optional<Platform> platformOptional = Optional.ofNullable(platformRepository.findById(idPlatform).orElseThrow(()-> new EntityNotFoundException("Plataforma não encontrado")));

        if (teamOptional.isPresent() && platformOptional.isPresent()){
            Team team = teamOptional.get();
            Platform platform = platformOptional.get();

            team.getPlatforms().remove(platform);
            teamRepository.save(team);
            return true;
        }else{
            return false;
        }
    }


}
