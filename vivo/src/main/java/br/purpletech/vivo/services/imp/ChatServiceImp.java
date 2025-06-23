package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.ChatRepository;
import br.purpletech.vivo.repositories.MessageRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.ChatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatServiceImp implements ChatService {
    private final ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatServiceImp(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat findOrCreateChat(Long id1, Long id2) {
        if (id1 == null || id2 == null) {
            throw new IllegalArgumentException("IDs não podem ser nulos");
        }

        List<Chat> allChats = chatRepository.findAll();

        return allChats.stream()
                .filter(chat -> {
                    Set<Long> participantIds = chat.getParticipants().stream()
                            .map(User::getId)
                            .collect(Collectors.toSet());
                    return participantIds.contains(id1)
                            && participantIds.contains(id2)
                            && participantIds.size() == 2;
                })
                .findFirst()
                .orElseGet(() -> {
                    User user1 = userRepository.findById(id1)
                            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id1 + " não encontrado"));
                    User user2 = userRepository.findById(id2)
                            .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id2 + " não encontrado"));

                    Chat chat = new Chat();
                    chat.getParticipants().add(user1);
                    chat.getParticipants().add(user2);
                    return chatRepository.save(chat);
                });
    }


    public Chat sendMessage(Long senderId, Long receiverId, Message message) {
        Chat chat = findOrCreateChat(senderId, receiverId);
        User sender = userRepository.findById(senderId).orElseThrow();

        message.setSender(sender);
        message.setChat(chat);

        messageRepository.save(message);
        chat.getMessages().add(message);
        return chatRepository.save(chat);
    }

    public List<Chat> getAllChatsByUserId(Long userId){
        return chatRepository.findByParticipantsId(userId);
    }



}
