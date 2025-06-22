package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.*;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OnboardingServiceImp onboardingServiceImp;

    @Autowired ChatServiceImp chatServiceImp;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updatedUser.getEmail());
            user.setPosition(updatedUser.getPosition());
            user.setRole(updatedUser.getRole());
            user.setTelephone(updatedUser.getTelephone());
            user.setName(updatedUser.getName());
            user.setLastName(updatedUser.getLastName());
            user.setTeam(updatedUser.getTeam());

            return userRepository.save(user);
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Chat sendMessageToUser(Long senderId, Long receiverId, Message message) {
        return chatServiceImp.sendMessage(senderId, receiverId, message);
    }

    public Chat getChatManager(Long idUser) {
        Onboarding onboarding = onboardingServiceImp.findManagerByCollaboratorId(idUser);
        Long managerId = onboarding.getManager().getId();
        return chatServiceImp.findOrCreateChat(idUser, managerId);
    }

    public Chat getChatBuddy(Long idUser) {
        Onboarding onboarding = onboardingServiceImp.findBuddyByCollaboratorId(idUser);
        Long buddyId = onboarding.getBuddy().getId();
        return chatServiceImp.findOrCreateChat(idUser, buddyId);
    }

    public Chat getChatWithUsers(Long user1Id, Long user2Id) {
        return chatServiceImp.findOrCreateChat(user1Id, user2Id);
    }


}