package org.siit.logisticsystem.actuator;


import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.actuate.autoconfigure.info.InfoContributorProperties;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomInfoEndpoint extends InfoEndpoint {

    public CustomInfoEndpoint(ApplicationContext context, InfoContributorProperties properties) {
        super(context, properties);
    }

    @Override
    public Map<String, Object> invoke() {
        Map<String, Object> info = super.invoke();
        // Customize the output format
        return info;
    }
}


