package devx.challenge.login.repositories;

import devx.challenge.login.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  UserEntity findByEmail(String email);
}
