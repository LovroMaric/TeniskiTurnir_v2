package Util;

import Model.AppConfig;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class ConfigLoader {

    private static AppConfig config;

    private ConfigLoader() {}

    public static AppConfig getConfig() {

        if (config == null) {

            try {
                JAXBContext context = JAXBContext.newInstance(AppConfig.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();

                InputStream is = ConfigLoader.class.getResourceAsStream("/config.xml");

                config = (AppConfig) unmarshaller.unmarshal(is);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return config;
    }
}
