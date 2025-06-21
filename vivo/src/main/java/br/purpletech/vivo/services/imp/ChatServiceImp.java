package br.purpletech.vivo.services.imp;

import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.User;
import br.purpletech.vivo.repositories.ChatRepository;
import br.purpletech.vivo.repositories.MessageRepository;
import br.purpletech.vivo.repositories.UserRepository;
import br.purpletech.vivo.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Chat createChat(User participant1, User participant2) {
        Chat newChat = new Chat();
        newChat.setParticipant1(participant1);
        newChat.setParticipant2(participant2);
        return chatRepository.save(newChat);
    }

    @Override
    public Optional<Chat> getChat(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    public Chat createMessages(Long id, Message message) {
        return chatRepository.findById(id).map(chat -> {
            chat.getMessages().add(message);
            message.setChat(chat);
            messageRepository.save(message);
            return chatRepository.save(chat);
        }).orElse(null);
    }

    @Override
    public Optional<Chat> findByParticipant1AndParticipant2(Long participant1Id, Long participant2Id) {
        Optional<Chat> chat = chatRepository.findByParticipant1IdAndParticipant2Id(participant1Id, participant1Id);
        if(chat.isEmpty()){
            Optional<User> userOptional1 = userRepository.findById(participant1Id);
            Optional<User> userOptional2 = userRepository.findById(participant2Id);

            if (userOptional1.isPresent() && userOptional2.isPresent()){
                createChat(userOptional1.get(), userOptional2.get());
            }
        }

        return chatRepository.findByParticipant1IdAndParticipant2Id(participant1Id, participant2Id);
    }


}
