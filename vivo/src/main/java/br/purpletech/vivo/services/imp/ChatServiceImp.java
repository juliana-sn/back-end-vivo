package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.dtos.chat.ChatDTO;
import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.ChatRepository;
import br.purpletech.vivo.repositories.MessageRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.ChatService;
import br.purpletech.vivo.utils.EntityDtoConverter;
import br.purpletech.vivo.exceptions.custom.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ChatDTO findOrCreateChatDTO(Long id1, Long id2) {
        Chat chat = findOrCreateChat(id1, id2);
        return EntityDtoConverter.toChatDTO(chat);
    }

    @Transactional
    public Chat findOrCreateChat(Long id1, Long id2) {
        if (id1 == null || id2 == null) {
            throw new IllegalArgumentException("IDs não podem ser nulos");
        }

        return chatRepository.findAll().stream()
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
                            .orElseThrow(UserNotFoundException::new);
                    User user2 = userRepository.findById(id2)
                            .orElseThrow(UserNotFoundException::new);

                    Chat chat = new Chat();
                    chat.getParticipants().add(user1);
                    chat.getParticipants().add(user2);
                    return chatRepository.save(chat);
                });
    }

    @Transactional
    public ChatDTO sendMessage(Long senderId, Long receiverId, Message message) {
        Chat chat = findOrCreateChat(senderId, receiverId);

        User sender = userRepository.findById(senderId)
                .orElseThrow(UserNotFoundException::new);

        message.setSender(sender);
        message.setChat(chat);
        messageRepository.save(message);

        chat.getMessages().add(message);
        Chat savedChat = chatRepository.save(chat);

        return EntityDtoConverter.toChatDTO(savedChat);
    }

    public List<ChatDTO> getAllChatsByUserId(Long userId){
        return chatRepository.findByParticipantsId(userId).stream()
                .map(EntityDtoConverter::toChatDTO)
                .collect(Collectors.toList());
    }
}