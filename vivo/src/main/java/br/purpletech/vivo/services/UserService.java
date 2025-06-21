package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getById(Long id);
    User createUser(User user);
    Optional<User> updateUser(Long id, User updatedUser);
    boolean deleteUser(Long id);
    List<User> getUsersByRole(Role role);

    Optional<Chat> getChatManager(Long idUser);
    Optional<Chat> getChatBuddy(Long idUser);
    //Optional<Chat> getChatCollaborator(Long idUser);

}
