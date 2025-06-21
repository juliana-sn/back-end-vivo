package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByParticipant1IdAndParticipant2Id(Long participant1Id, Long participant2Id);
}
