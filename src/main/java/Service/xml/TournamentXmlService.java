package Service.xml;

import Model.Tournament;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.File;

public class TournamentXmlService {

    public void export(Tournament tournament, String path) {

        try {
            JAXBContext context = JAXBContext.newInstance(Tournament.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(tournament, new File(path));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
