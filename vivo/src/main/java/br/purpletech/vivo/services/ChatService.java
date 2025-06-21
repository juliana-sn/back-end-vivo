package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.User;

import java.util.Optional;

public interface ChatService {
    Chat createChat(User participant1, User participant2);
    Optional<Chat> getChat(Long id);
    Chat createMessages(Long id, Message message);
    Optional<Chat> findByParticipant1AndParticipant2(Long participant1Id, Long participant2Id);
}
