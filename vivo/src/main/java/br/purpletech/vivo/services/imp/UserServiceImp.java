package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.chat.ChatDTO;
import br.purpletech.vivo.dtos.user.UserDTO;
import br.purpletech.vivo.dtos.user.UserToCreateDTO;
import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.UserService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TeamServiceImp teamServiceImp;

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

    public Optional<UserDTO> updateUserTeam(Long userId, Long newTeamId) {
        return userRepository.findById(userId).map(user -> {
            Optional<Team> teamOptional = Optional.ofNullable(teamServiceImp.getById(newTeamId).orElseThrow(() -> new EntityNotFoundException("Equipe não encontrada")));

            if(teamOptional.isPresent()){
                Team team = teamOptional.get();
                team.getUsers().add(user);
                user.setTeam(team);
                User updated = userRepository.save(user);
                return EntityDtoConverter.toUserDTO(updated);
            }else{
                return null;
            }
        });
    }


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

    public ChatDTO sendMessageToUser(Long senderId, Long receiverId, Message message) {
        return chatServiceImp.sendMessage(senderId, receiverId, message);
    }

    public ChatDTO getChatManager(Long idUser) {
        Onboarding onboarding = onboardingServiceImp.findManagerByCollaboratorId(idUser);
        Long managerId = onboarding.getManager().getId();
        return chatServiceImp.findOrCreateChat(idUser, managerId);
    }

    public ChatDTO getChatBuddy(Long idUser) {
        Onboarding onboarding = onboardingServiceImp.findBuddyByCollaboratorId(idUser);
        Long buddyId = onboarding.getBuddy().getId();
        return chatServiceImp.findOrCreateChat(idUser, buddyId);
    }

    public ChatDTO getChatWithUsers(Long senderId, Long receiverId) {
        return chatServiceImp.findOrCreateChat(senderId, receiverId);
    }

    public List<ChatDTO> getAllChatsByUserId(Long idUser){
        return chatServiceImp.getAllChatsByUserId(idUser);
    }


}