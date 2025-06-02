package my.petstore.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${env}.properties"})
public interface HostProperties extends Config {
    @Key("base.url")
    String baseUrl();
}