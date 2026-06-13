package Util;

import Model.LogEntries;
import Model.LogEntry;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.time.LocalDateTime;

public class XmlLogger {

    private static final String LOG_FILE = "log.xml";

    private XmlLogger() {}

    public static void log(String username, String action) {

        try {

            JAXBContext context = JAXBContext.newInstance(LogEntries.class);

            LogEntries logEntries;
            File file = new File(LOG_FILE);

            if (file.exists()) {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                logEntries = (LogEntries) unmarshaller.unmarshal(file);
            } else {
                logEntries = new LogEntries();
            }

            logEntries.getEntries().add(
                    new LogEntry(LocalDateTime.now().toString(), username, action)
            );

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(logEntries, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}