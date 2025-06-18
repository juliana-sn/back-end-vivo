package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.repositories.TeamRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() { return teamRepository.findAll();}

    public Optional<Team> getById(Long id) {
        return teamRepository.findById(id);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }


}
