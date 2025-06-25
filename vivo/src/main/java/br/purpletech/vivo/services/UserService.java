package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.chat.ChatDTO;
import br.purpletech.vivo.dtos.message.MessageToCreateDTO;
import br.purpletech.vivo.dtos.user.UserDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getById(Long id);
    Optional<UserDTO> updateUser(Long id, UserToCreateDTO updatedUser);
    Optional<UserDTO> updateUserTeam(Long userId, Long newTeamId);
    boolean deleteUser(Long id);
    List<UserDTO> getUsersByRole(Role role);

    ChatDTO sendMessageToUser(Long senderId, Long receiverId, MessageToCreateDTO message);
    ChatDTO getChatManager(Long idUser);
    ChatDTO getChatBuddy(Long idUser);
    ChatDTO getChatWithUsers(Long senderId, Long receiverId);
    List<ChatDTO> getAllChatsByUserId(Long idUser);
}
