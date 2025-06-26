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
    TeamDTO getById(Long id);
    TeamDTO createTeam(TeamToCreateDTO team);
    TeamDTO updateNameTeam(Long id, TeamToCreateDTO updatedTeam);
    void deleteTeam(Long id);
    boolean deleteUser(Long idTeam, Long idUser);

    TeamDTO addPlatform(Long id, Long idPlatform);
    boolean deletePlatform(Long idTeam, Long idPlatform);

}
