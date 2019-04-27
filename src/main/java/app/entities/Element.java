package app.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "elements")
public class Element implements Serializable {

    @Id
    private String elementId;
    private String userId;
    private Timestamp createdOn;
    private String content;
}
