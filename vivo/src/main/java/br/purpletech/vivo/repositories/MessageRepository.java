package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
