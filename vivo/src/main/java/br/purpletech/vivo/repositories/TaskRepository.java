package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStardardTrue();
}
