package br.purpletech.vivo.controllers;


import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.services.imp.TeamServiceImp;
import br.purpletech.vivo.services.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImp userServiceImp;

    @Autowired
    private TeamServiceImp teamServiceImp;

    public UserController(UserServiceImp userServiceImp){
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userServiceImp.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id){
        var user = userServiceImp.getById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User userToCreate){
        Optional<Team> teamOptional = Optional.ofNullable(teamServiceImp.getById(userToCreate.getTeam().getId()).orElseThrow(() ->
                new RuntimeException("Equipe não encontrada")));

        if(teamOptional.isPresent()){
            Team team = teamOptional.get();
            userToCreate.setTeam(team);
        }

        User createdUser = userServiceImp.createUser(userToCreate);

        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userServiceImp.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable Long id, User updateUser){
        var updatedUser = userServiceImp.updateUser(id, updateUser);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/role")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam Role role){
        var users = userServiceImp.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
}
