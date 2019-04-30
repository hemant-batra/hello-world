package app.jpa.repositories;

import app.jpa.entities.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.stream.Stream;

@Repository
public interface ElementsRepository extends JpaRepository<Element, String> {

    Stream<Element> streamAll();
    Stream<Element> streamAllByCreatedOnAfter(Timestamp createdOn);
}
