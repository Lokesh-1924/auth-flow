package mini_projects.authServer.repository;

import mini_projects.authServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query("Update User u SET u.status = 'DELETED' Where u.id = :id")
    void softDelete(@Param("id") UUID id);

    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE'")
    List<User> findAllActiveUsers();

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
