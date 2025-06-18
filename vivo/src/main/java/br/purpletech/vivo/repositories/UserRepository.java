package br.purpletech.vivo.repositories;

import br.purpletech.vivo.models.Role;
import br.purpletech.vivo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole (Role role);

}
