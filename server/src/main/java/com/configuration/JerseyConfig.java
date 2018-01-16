package com.configuration;

import com.rest.MessagesResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(MessagesResource.class);
		property(ServerProperties.WADL_FEATURE_DISABLE, true);
	}

}
