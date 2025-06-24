package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.team.TeamDTO;
import br.purpletech.vivo.dtos.team.TeamToCreateDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDTO> getAllTeams();
    Optional<TeamDTO> getById(Long id);
    TeamDTO createTeam(TeamToCreateDTO team);
    Optional<TeamDTO> updateNameTeam(Long id, TeamToCreateDTO updatedTeam);
    boolean deleteTeam(Long id);

    TeamDTO addUser(Long id, UserToCreateDTO user);
    boolean deleteUser(Long idTeam, Long idUser);

    TeamDTO addPlatform(Long id, Long idPlatform);
    boolean deletePlatform(Long idTeam, Long idPlatform);

}
