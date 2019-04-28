package app.repositories;

import app.entities.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementsRepository extends JpaRepository<Element, String> {
}
