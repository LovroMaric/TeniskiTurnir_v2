package Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "appConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppConfig {

    @XmlElement
    private String dbUrl;

    @XmlElement
    private String dbUser;

    @XmlElement
    private String dbPassword;

    @XmlElement
    private int screenWidth;

    @XmlElement
    private int screenHeight;

    public String getDbUrl() { return dbUrl; }
    public String getDbUser() { return dbUser; }
    public String getDbPassword() { return dbPassword; }
    public int getScreenWidth() { return screenWidth; }
    public int getScreenHeight() { return screenHeight; }
}