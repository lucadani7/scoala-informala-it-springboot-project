package org.siit.logisticsystem.actuator;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.actuate.autoconfigure.info.InfoContributorProperties;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.info.InfoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Bean
    public InfoProperties infoProperties() {
        InfoProperties properties;
        properties = new InfoProperties();
        properties.setDetails ( false );
        properties.setEnabled(true);
        properties.setGit(new GitInfoProperties());
        properties.setBuild(new BuildInfoProperties());
        return properties;
    }

    @Bean
    public InfoEndpoint infoEndpoint(ApplicationContext context, InfoProperties infoProperties) {
        InfoEndpoint endpoint = new CustomInfoEndpoint(context, new InfoContributorProperties());
        endpoint.setInfoProperties(infoProperties);
        return endpoint;
    }
}