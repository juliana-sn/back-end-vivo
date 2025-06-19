package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    public List<Team> getAllTeams();
    public Optional<Team> getById(Long id);
    public Team createTeam(Team team);
    public Optional<Team> updateNameTeam(Long id, Team updatedTeam);
    public boolean deleteTeam(Long id);

    public Team addUser(Long id, User user);
    public boolean deleteUser(Long id_team, Long id_user);

}
