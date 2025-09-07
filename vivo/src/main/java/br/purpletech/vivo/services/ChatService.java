package br.purpletech.vivo.services;

import br.purpletech.vivo.dtos.chat.ChatDTO;
import br.purpletech.vivo.dtos.message.MessageToCreateDTO;
import br.purpletech.vivo.models.Chat;
import br.purpletech.vivo.models.Message;
import br.purpletech.vivo.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Chat findOrCreateChat(Long id1, Long id2);
    ChatDTO sendMessage(Long senderId, Long receiverId, MessageToCreateDTO message);
    List<ChatDTO> getAllChatsByUserId(Long userId);
    ChatDTO findOrCreateChatDTO(Long id1, Long id2);
}
