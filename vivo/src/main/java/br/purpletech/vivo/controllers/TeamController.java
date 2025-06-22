package br.purpletech.vivo.controllers;

import br.purpletech.vivo.models.Platform;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.services.imp.TeamServiceImp;
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
    public ResponseEntity<List<Team>> getAllTeams(){
        var teams = teamServiceImp.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Team>> getTeamById(@PathVariable Long id){
        var team = teamServiceImp.getById(id);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team teamToCreate){
        var createdTeam = teamServiceImp.createTeam(teamToCreate);
        return ResponseEntity.ok(createdTeam);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Team>> updateTeam(@PathVariable Long id, @RequestBody Team updateTeam){
        var updatedTeam = teamServiceImp.updateNameTeam(id, updateTeam);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        teamServiceImp.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<Team> addUser(@PathVariable Long id, @RequestBody User userToCreate){
        var team = teamServiceImp.addUser(id, userToCreate);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{id}/users/{id_user}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @PathVariable Long id_user){
        teamServiceImp.deleteUser(id, id_user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/platforms/{id_platform}")
    public ResponseEntity<Team> addPlatform(@PathVariable Long id, @PathVariable Long id_platform){
        var team = teamServiceImp.addPlatform(id, id_platform);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{id}/platforms/{id_platform}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id, @PathVariable Long id_platform){
        teamServiceImp.deletePlatform(id, id_platform);
        return ResponseEntity.noContent().build();
    }
}
