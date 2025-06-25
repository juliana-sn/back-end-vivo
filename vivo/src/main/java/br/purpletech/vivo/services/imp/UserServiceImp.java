package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.chat.ChatDTO;
import br.purpletech.vivo.dtos.message.MessageToCreateDTO;
import br.purpletech.vivo.dtos.onboarding.OnboardingDTO;
import br.purpletech.vivo.dtos.user.UserDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.TeamRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.UserService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnboardingServiceImp onboardingServiceImp;

    @Autowired ChatServiceImp chatServiceImp;

    @Autowired
    private TeamRepository teamRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(EntityDtoConverter::toUserDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getById(Long id) {
        return userRepository.findById(id)
                .map(EntityDtoConverter::toUserDTO);
    }

    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserToCreateDTO updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updatedUser.email());
            user.setPosition(updatedUser.position());
            user.setRole(updatedUser.role());
            user.setTelephone(updatedUser.telephone());
            user.setName(updatedUser.name());
            user.setLastName(updatedUser.lastName());

            User userUpdated = userRepository.save(user);
            return EntityDtoConverter.toUserDTO(userUpdated);
        });
    }

    @Transactional
    public Optional<UserDTO> updateUserTeam(Long userId, Long newTeamId) {
        return userRepository.findById(userId).map(user -> {
            // Remove da equipe atual
            Team currentTeam = user.getTeam();
            if (currentTeam != null) {
                currentTeam.getUsers().remove(user);
                teamRepository.save(currentTeam);
            }

            Team newTeam = teamRepository.findById(newTeamId)
                    .orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada"));

            user.setTeam(newTeam);
            newTeam.getUsers().add(user);

            teamRepository.save(newTeam);
            User updatedUser = userRepository.save(user);

            return EntityDtoConverter.toUserDTO(updatedUser);
        });
    }



    @Transactional
    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public List<UserDTO> getUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(EntityDtoConverter::toUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatDTO sendMessageToUser(Long senderId, Long receiverId, MessageToCreateDTO messageToCreate) {
        Message message = EntityDtoConverter.toMessage(messageToCreate);
        return chatServiceImp.sendMessage(senderId, receiverId, message);
    }

    public ChatDTO getChatManager(Long idUser) {
        OnboardingDTO onboarding = onboardingServiceImp.findManagerByCollaboratorId(idUser);
        Long managerId = onboarding.manager().id();
        Chat chatFound = chatServiceImp.findOrCreateChat(idUser, managerId);
        return EntityDtoConverter.toChatDTO(chatFound);
    }

    public ChatDTO getChatBuddy(Long idUser) {
        OnboardingDTO onboarding = onboardingServiceImp.findBuddyByCollaboratorId(idUser);
        Long buddyId = onboarding.buddy().id();
        Chat chatFound = chatServiceImp.findOrCreateChat(idUser, buddyId);
        return EntityDtoConverter.toChatDTO(chatFound);
    }

    public ChatDTO getChatWithUsers(Long senderId, Long receiverId) {
        Chat chatFound = chatServiceImp.findOrCreateChat(senderId, receiverId);
        return EntityDtoConverter.toChatDTO(chatFound);
    }

    public List<ChatDTO> getAllChatsByUserId(Long idUser){
        return chatServiceImp.getAllChatsByUserId(idUser);
    }


}