package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> getAllTeams();
    Optional<Team> getById(Long id);
    Team createTeam(Team team);
    Optional<Team> updateNameTeam(Long id, Team updatedTeam);
    boolean deleteTeam(Long id);

    Team addUser(Long id, User user);
    boolean deleteUser(Long id_team, Long id_user);

    Team addPlatform(Long id, Long id_platform);
    boolean deletePlatform(Long id_team, Long id_platform);

}
