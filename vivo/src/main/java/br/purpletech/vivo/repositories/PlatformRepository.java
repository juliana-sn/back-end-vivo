package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    boolean existsByName(String name);
}
