package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByParticipantsId (Long userId);
}
