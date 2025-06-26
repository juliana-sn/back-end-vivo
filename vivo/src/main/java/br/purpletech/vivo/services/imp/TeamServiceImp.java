package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.team.TeamDTO;
import br.purpletech.vivo.dtos.team.TeamToCreateDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.exceptions.custom.team.*;
import br.purpletech.vivo.exceptions.custom.platform.PlatformNotFoundException;
import br.purpletech.vivo.exceptions.custom.user.EmailAlreadyUsedException;
import br.purpletech.vivo.exceptions.custom.user.UserNotFoundException;
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

    public TeamDTO getById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(TeamNotFoundException::new);
        return EntityDtoConverter.toTeamDTO(team);
    }


    @Transactional
    public TeamDTO createTeam(TeamToCreateDTO teamToCreate) {
        if (teamRepository.existsByName(teamToCreate.name())) {
            throw new TeamNameAlreadyUsedException();
        }

        Team team = EntityDtoConverter.toTeam(teamToCreate);
        Team savedTeam = teamRepository.save(team);

        return EntityDtoConverter.toTeamDTO(savedTeam);
    }


    @Transactional
    @Override
    public TeamDTO updateNameTeam(Long id, TeamToCreateDTO updateTeam) {
        Team team = teamRepository.findById(id)
                .orElseThrow(TeamNotFoundException::new);

        if (teamRepository.existsByName(updateTeam.name()) && !team.getName().equalsIgnoreCase(updateTeam.name())) {
            throw new TeamNameAlreadyUsedException();
        }

        team.setName(updateTeam.name());
        Team savedTeam = teamRepository.save(team);

        return EntityDtoConverter.toTeamDTO(savedTeam);
    }


    @Transactional
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(TeamNotFoundException::new);

        teamRepository.delete(team);
    }

    @Transactional
    @Override
    public boolean deleteUser(Long idTeam, Long idUser) {
        Team team = teamRepository.findById(idTeam)
                .orElseThrow(TeamNotFoundException::new);

        User user = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        team.getUsers().remove(user);
        user.setTeam(null);
        teamRepository.save(team);
        userRepository.save(user);
        return true;
    }


    @Transactional
    @Override
    public TeamDTO addPlatform(Long id, Long idPlatform) {
        Team team = teamRepository.findById(id)
                .orElseThrow(TeamNotFoundException::new);

        Platform platform = platformRepository.findById(idPlatform)
                .orElseThrow(PlatformNotFoundException::new);

        team.getPlatforms().add(platform);
        platform.getTeams().add(team);

        platformRepository.save(platform);
        Team teamSaved = teamRepository.save(team);

        return EntityDtoConverter.toTeamDTO(teamSaved);
    }

    @Transactional
    @Override
    public boolean deletePlatform(Long idTeam, Long idPlatform) {
        Team team = teamRepository.findById(idTeam)
                .orElseThrow(TeamNotFoundException::new);

        Platform platform = platformRepository.findById(idPlatform)
                .orElseThrow(PlatformNotFoundException::new);

        team.getPlatforms().remove(platform);
        teamRepository.save(team);
        return true;
    }
}
