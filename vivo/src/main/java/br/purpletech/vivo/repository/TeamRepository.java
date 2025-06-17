package br.purpletech.vivo.repository;

import br.purpletech.vivo.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
