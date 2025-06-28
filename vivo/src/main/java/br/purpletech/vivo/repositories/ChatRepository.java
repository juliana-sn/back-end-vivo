package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("""
    SELECT c FROM tb_chats c 
    JOIN c.participants p 
    WHERE p.id = :userId
""")
    List<Chat> findByParticipantsId (@Param("userId") Long userId);
}
