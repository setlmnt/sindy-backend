package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.user.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Modifying
    @Query(
            "UPDATE Otp o SET o.deleted = true, o.deletedAt = CAST(UTC_TIMESTAMP() AS java.time.OffsetDateTime) WHERE o.deleted = false AND o.user.username = :username"
    )
    void deleteAllByUsername(@Param("username") String username);

    @Query(
            "SELECT o FROM Otp o WHERE o.deleted = false AND o.user.username = :username AND o.code = :code"
    )
    Otp findByCodeAndUsername(@Param("code") String code, @Param("username") String username);
}
