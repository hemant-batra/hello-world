package app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "elements")
public class Element implements Serializable {

    @Id
    private String id;
    private Timestamp createdOn;
    private String ipAddress;
    private String userName;
    private String content;
}
