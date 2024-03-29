package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsernameAndDeletedFalse(String username);

    User findUserByUsername(String username);

    User findUserByUsernameAndDeletedFalse(String name);
}
