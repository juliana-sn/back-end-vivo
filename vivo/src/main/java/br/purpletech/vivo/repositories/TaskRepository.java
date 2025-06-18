package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
