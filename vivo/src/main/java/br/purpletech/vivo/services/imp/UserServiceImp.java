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

    public Optional<Chat> getChatManager(Long idUser) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));

        if (userOptional.isPresent()) {
            Onboarding onboarding = onboardingServiceImp.findManagerByCollaboratorId(idUser);
            Long managerId = onboarding.getManager().getId();
            return chatServiceImp.findByParticipant1AndParticipant2(idUser, managerId);
        }else{
            return null;
        }
    }

    public Optional<Chat> getChatBuddy(Long idUser){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));

        if (userOptional.isPresent()) {
            Onboarding onboarding = onboardingServiceImp.findBuddyByCollaboratorId(idUser);
            Long buddyId = onboarding.getBuddy().getId();
            return chatServiceImp.findByParticipant1AndParticipant2(idUser, buddyId);
        }else{
            return null;
        }
    }

    /*
    public Optional<Chat> getChatCollaborator(Long idUser){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));

        if (userOptional.isPresent() && userOptional.get().getRole() == Role.MANAGER) {
            Optional<Long> optionalManagerId = Optional.ofNullable(onboardingServiceImp.findCollaboratorIdByManagerId(idUser).orElseThrow(() -> new RuntimeException("Manager não encontrado")));
            Long managerId = optionalManagerId.get();
            return chatServiceImp.findByParticipant1AndParticipant2(idUser, managerId);

        } else if (userOptional.isPresent() && userOptional.get().getRole() == Role.BUDDY) {
            Optional<Long> optionalBuddyId = Optional.ofNullable(onboardingServiceImp.findCollaboratorIdByBuddyId(idUser).orElseThrow(() -> new RuntimeException("Manager não encontrado")));
            Long buddyId = optionalBuddyId.get();
            return chatServiceImp.findByParticipant1AndParticipant2(idUser, buddyId);

        }else{
            return null;
        }
    }

    public Chat sendMessageCollaborator(Long idUser, Message message){
        Optional<Chat> optionalChat = getChatCollaborator(idUser);
        Chat chat = optionalChat.get();
        Long id = chat.getId();
        return chatServiceImp.createMessages(id, message);
    }

     */

    public Chat sendMessageManager(Long idUser, Message message){
        Optional<Chat> optionalChat = getChatManager(idUser);
        Chat chat = optionalChat.get();
        Long id = chat.getId();
        return chatServiceImp.createMessages(id, message);
    }

    public Chat sendMessageBuddy(Long idUser, Message message){
        Optional<Chat> optionalChat = getChatBuddy(idUser);
        Chat chat = optionalChat.get();
        Long id = chat.getId();
        return chatServiceImp.createMessages(id, message);
    }

}