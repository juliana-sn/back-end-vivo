package br.purpletech.vivo.controllers;


import br.purpletech.vivo.models.*;
import br.purpletech.vivo.services.imp.TeamServiceImp;
import br.purpletech.vivo.services.imp.UserServiceImp;
import org.apache.coyote.Response;
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

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable Long id, User updateUser){
        var updatedUser = userServiceImp.updateUser(id, updateUser);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/role")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam Role role){
        var users = userServiceImp.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{idSender}/chat/{idReceiver}/message")
    public ResponseEntity<Chat> sendMessageToUser(@PathVariable Long idSender,
                                                  @PathVariable Long idReceiver,
                                                  @RequestBody Message message) {
        var chat = userServiceImp.sendMessageToUser(idSender, idReceiver, message);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{id}/chat/manager")
    public ResponseEntity<Chat> getChatManager(@PathVariable Long id){
        var chat = userServiceImp.getChatManager(id);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{id}/chat/buddy")
    public ResponseEntity<Chat> getChatBuddy(@PathVariable Long id){
        var chat = userServiceImp.getChatBuddy(id);
        return ResponseEntity.ok(chat);
    }


    @GetMapping("/{senderId}/chat/{receiverId}")
    public ResponseEntity<Chat> getOrCreateChatWithUsers(@PathVariable Long senderId, @PathVariable Long receiverId) {
        var chat = userServiceImp.getChatWithUsers(senderId, receiverId);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{userId}/chats")
    public ResponseEntity<List<Chat>> getAllChatsByUserId(@PathVariable Long userId){
        var chats = userServiceImp.getAllChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

}
