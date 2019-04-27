package app.repositories;

import app.entities.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementsRepository extends JpaRepository<Element, String> {
    List<Element> findAllByUserId(String userId);
}
