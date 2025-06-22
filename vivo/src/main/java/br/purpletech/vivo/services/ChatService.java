package br.purpletech.vivo.services;

import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.User;

import java.util.Optional;

public interface ChatService {
    Chat findOrCreateChat(Long id1, Long id2);
    Chat sendMessage(Long senderId, Long receiverId, Message message);
}
