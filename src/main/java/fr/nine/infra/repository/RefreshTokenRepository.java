package fr.nine.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.nine.domain.enterprise.user.entities.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);
}
