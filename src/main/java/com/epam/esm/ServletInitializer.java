package com.epam.esm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The ServletInitializer class extends SpringBootServletInitializer to configure the Spring Boot application
 * when it is deployed as a WAR (Web Application Archive) in a servlet container. It specifies the main
 * application class that should be used as the source for configuration.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
