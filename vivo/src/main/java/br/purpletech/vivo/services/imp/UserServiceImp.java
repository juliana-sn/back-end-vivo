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
import br.purpletech.vivo.exceptions.custom.user.*;
import br.purpletech.vivo.exceptions.custom.team.TeamNotFoundException;

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

    public UserDTO getById(Long id) {
        return userRepository.findById(id)
                .map(EntityDtoConverter::toUserDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserToCreateDTO updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        user.setEmail(updatedUser.email());
        user.setPosition(updatedUser.position());
        user.setRole(updatedUser.role());
        user.setTelephone(updatedUser.telephone());
        user.setName(updatedUser.name());
        user.setLastName(updatedUser.lastName());

        User userUpdated = userRepository.save(user);
        return EntityDtoConverter.toUserDTO(userUpdated);
    }

    @Transactional
    public UserDTO updateUserTeam(Long userId, Long newTeamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Team newTeam = teamRepository.findById(newTeamId)
                .orElseThrow(TeamNotFoundException::new);

        Team currentTeam = user.getTeam();
        if (currentTeam != null && !currentTeam.equals(newTeam)) {
            currentTeam.getUsers().remove(user);
        }

        user.setTeam(newTeam);
        newTeam.getUsers().add(user);

        return EntityDtoConverter.toUserDTO(userRepository.save(user));
    }


    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Transactional
    public ChatDTO sendMessageToUser(Long senderId, Long receiverId, MessageToCreateDTO messageToCreate) {
        return chatServiceImp.sendMessage(senderId, receiverId, messageToCreate);
    }

    public ChatDTO getChatManager(Long idUser) {
        User collaborator = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        if (collaborator.getRole() != Role.COLLABORATOR) {
            throw new IllegalStateException("Usuário informado não é um colaborador.");
        }

        Onboarding onboarding = collaborator.getOnboarding().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Colaborador não está vinculado a nenhum onboarding."));

        User manager = onboarding.getUsers().stream()
                .filter(user -> user.getRole() == Role.MANAGER)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhum usuário gestor encontrado."));

        Chat chat = chatServiceImp.findOrCreateChat(collaborator.getId(), manager.getId());
        return EntityDtoConverter.toChatDTO(chat);
    }

    public ChatDTO getChatBuddy(Long idUser) {
        User collaborator = userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);

        if (collaborator.getRole() != Role.COLLABORATOR) {
            throw new IllegalStateException("Usuário informado não é um colaborador.");
        }

        Onboarding onboarding = collaborator.getOnboarding().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Colaborador não está vinculado a nenhum onboarding."));

        User buddy = onboarding.getUsers().stream()
                .filter(user -> user.getRole() == Role.BUDDY)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhum usuário buddy encontrado."));

        Chat chat = chatServiceImp.findOrCreateChat(collaborator.getId(), buddy.getId());
        return EntityDtoConverter.toChatDTO(chat);
    }

    public ChatDTO getChatWithUsers(Long senderId, Long receiverId) {
        Chat chatFound = chatServiceImp.findOrCreateChat(senderId, receiverId);
        return EntityDtoConverter.toChatDTO(chatFound);
    }

    public List<ChatDTO> getAllChatsByUserId(Long idUser){
        return chatServiceImp.getAllChatsByUserId(idUser);
    }


}