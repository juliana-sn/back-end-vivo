package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.PlatformRepository;
import br.purpletech.vivo.repositories.TeamRepository;

import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.TeamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Team> getAllTeams() { return teamRepository.findAll();}

    public Optional<Team> getById(Long id) {
        return teamRepository.findById(id);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> updateNameTeam(Long id, Team updateTeam) {
        return teamRepository.findById(id).map(team -> {
            team.setName(updateTeam.getName());
            return teamRepository.save(team);
        });
    }

    @Override
    public boolean deleteTeam(Long id) {
        return teamRepository.findById(id).map(team -> {
            teamRepository.delete(team);
            return true;
        }).orElse(false);
    }

    @Override
    public Team addUser(Long id, User user) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipe não encontrada")));
        if(teamOptional.isPresent()) {
            Team team = teamOptional.get();

            User savedUser = userRepository.save(user);

            savedUser.setTeam(team);
            team.getUsers().add(savedUser);
            return teamRepository.save(team);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteUser(Long id_team, Long id_user) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(id_team).orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada")));

        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id_user).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado")));

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

    @Override
    public Team addPlatform(Long id, Long id_platform) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipe não encontrada")));
        Optional<Platform> platformOptional = Optional.ofNullable(platformRepository.findById(id_platform).orElseThrow(() -> new RuntimeException("Plataforma não encontrada")));

        if(teamOptional.isPresent() && platformOptional.isPresent()) {
            Team team = teamOptional.get();
            Platform platform = platformOptional.get();

            team.getPlatforms().add(platform);
            platform.getTeams().add(team);

            platformRepository.save(platform);
            return teamRepository.save(team);
        }else{
            return null;
        }
    }

    @Override
    public boolean deletePlatform(Long id_team, Long id_platform) {
        Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findById(id_team).orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada")));

        Optional<Platform> platformOptional = Optional.ofNullable(platformRepository.findById(id_platform).orElseThrow(()-> new EntityNotFoundException("Plataforma não encontrado")));

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
