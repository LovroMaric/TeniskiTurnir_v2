package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LogEntry {

    @XmlElement
    private String timestamp;

    @XmlElement
    private String username;

    @XmlElement
    private String action;

    public LogEntry() {}

    public LogEntry(String timestamp, String username, String action) {
        this.timestamp = timestamp;
        this.username = username;
        this.action = action;
    }

    public String getTimestamp() { return timestamp; }
    public String getUsername() { return username; }
    public String getAction() { return action; }
}