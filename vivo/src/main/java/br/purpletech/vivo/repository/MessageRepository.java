package br.purpletech.vivo.repository;

import br.purpletech.vivo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
