package br.purpletech.vivo.controllers;


import br.purpletech.vivo.dtos.chat.ChatDTO;
import br.purpletech.vivo.dtos.message.MessageToCreateDTO;
import br.purpletech.vivo.dtos.user.UserDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.services.imp.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp){
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        var users = userServiceImp.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        var user = userServiceImp.getById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userServiceImp.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid UserToCreateDTO updateUser){
        var updatedUser = userServiceImp.updateUser(id, updateUser);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/team")
    public ResponseEntity<UserDTO> updateUserTeam(@PathVariable Long id, @RequestParam Long teamId) {
        UserDTO updatedUser = userServiceImp.updateUserTeam(id, teamId);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{idSender}/chat/{idReceiver}/message")
    public ResponseEntity<ChatDTO> sendMessageToUser(@PathVariable Long idSender,
                                                     @PathVariable Long idReceiver,
                                                     @RequestBody @Valid MessageToCreateDTO message) {
        ChatDTO chat = userServiceImp.sendMessageToUser(idSender, idReceiver, message);
        return ResponseEntity.status(201).body(chat);
    }


    @GetMapping("/{id}/chat/manager")
    public ResponseEntity<ChatDTO> getChatManager(@PathVariable Long id){
        var chat = userServiceImp.getChatManager(id);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{id}/chat/buddy")
    public ResponseEntity<ChatDTO> getChatBuddy(@PathVariable Long id){
        var chat = userServiceImp.getChatBuddy(id);
        return ResponseEntity.ok(chat);
    }


    @GetMapping("/{senderId}/chat/{receiverId}")
    public ResponseEntity<ChatDTO> getOrCreateChatWithUsers(@PathVariable Long senderId, @PathVariable Long receiverId) {
        var chat = userServiceImp.getChatWithUsers(senderId, receiverId);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/{userId}/chats")
    public ResponseEntity<List<ChatDTO>> getAllChatsByUserId(@PathVariable Long userId){
        var chats = userServiceImp.getAllChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

}
