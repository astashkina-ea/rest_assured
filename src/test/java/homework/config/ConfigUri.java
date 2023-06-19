package homework.config;

import org.aeonbits.owner.Config;

public interface ConfigUri extends Config{

    @Config.Key("baseUri")
    @Config.DefaultValue("https://reqres.in")
    String getBaseUri();
}