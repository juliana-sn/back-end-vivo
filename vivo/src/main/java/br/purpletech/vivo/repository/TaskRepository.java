package br.purpletech.vivo.repository;

import br.purpletech.vivo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
