package my.petstore.data;

import lombok.Getter;
import lombok.Setter;
import org.aeonbits.owner.ConfigFactory;
import my.petstore.config.HostProperties;

public class StoreClientData {
    private final HostProperties hostProperties;

    @Getter
    private final String environment;

    @Getter
    @Setter
    private String baseUrl;

    private String setEnvironment() {
        if(System.getProperty("env") == null) System.setProperty("env", "dev"); // fallback to dev, in case no env is provided
        return System.getProperty("env").toLowerCase();
    }

    public StoreClientData() {
        this.environment = setEnvironment();
        this.hostProperties = ConfigFactory.create(HostProperties.class);
        this.baseUrl = hostProperties.baseUrl();
    }
}
