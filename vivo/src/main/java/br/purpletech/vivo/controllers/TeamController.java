package br.purpletech.vivo.controllers;

import br.purpletech.vivo.dtos.team.TeamDTO;
import br.purpletech.vivo.dtos.team.TeamToCreateDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.services.imp.TeamServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamServiceImp teamServiceImp;

    public TeamController(TeamServiceImp teamServiceImp) {
        this.teamServiceImp = teamServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(){
        var teams = teamServiceImp.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id){
        var team = teamServiceImp.getById(id);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamToCreateDTO teamToCreate){
        var createdTeam = teamServiceImp.createTeam(teamToCreate);
        return ResponseEntity.ok(createdTeam);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody @Valid TeamToCreateDTO updateTeam){
        var updatedTeam = teamServiceImp.updateNameTeam(id, updateTeam);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamServiceImp.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/users/{idUser}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @PathVariable Long idUser){
        teamServiceImp.deleteUser(id, idUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/platforms/{idPlatform}")
    public ResponseEntity<TeamDTO> addPlatform(@PathVariable Long id, @PathVariable Long idPlatform){
        var team = teamServiceImp.addPlatform(id, idPlatform);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{id}/platforms/{idPlatform}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id, @PathVariable Long idPlatform){
        teamServiceImp.deletePlatform(id, idPlatform);
        return ResponseEntity.noContent().build();
    }
}
