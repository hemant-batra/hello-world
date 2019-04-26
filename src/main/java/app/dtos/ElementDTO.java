package app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ElementDTO {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy HH:mm:ss", timezone = "Asia/Kolkata")
    private Timestamp createdOn;
    private String ipAddress;
    private String userName;
    private String content;
}
