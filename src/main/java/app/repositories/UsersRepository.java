package app.repositories;

import app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    List<User> findAllByIpAddress(String ipAddress);
}
