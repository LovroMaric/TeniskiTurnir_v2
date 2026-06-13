package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "logEntries")
@XmlAccessorType(XmlAccessType.FIELD)
public class LogEntries {

    @XmlElement(name = "logEntry")
    private List<LogEntry> entries = new ArrayList<>();

    public List<LogEntry> getEntries() { return entries; }

    public void setEntries(List<LogEntry> entries) { this.entries = entries; }
}