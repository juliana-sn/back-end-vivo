package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Team;
import br.purpletech.vivo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
