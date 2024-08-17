package fr.nine.security.repository;

import fr.nine.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

}
