package com.example.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * - Run:
 * java -jar -Dserver.port=8080 target\spring-boot-base-api-0.1.0.jar
 *
 * - API Document
 * http://localhost:8080/swagger-ui.html#/
 */

@Slf4j
@SpringBootApplication
public class SpringBootAPIApplication {

    private static RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

    public static final String PROPERTIES = "spring.config.location=" +
            "classpath:application.yml" +
            ",classpath:sql.yml";

    public static final String PID = runtimeBean.getName().split("@")[0];

    public static void main(String[] args) {

        // SpringApplication.run(SpringBootAPIApplication.class, args);
        new SpringApplicationBuilder(SpringBootAPIApplication.class)
                .properties(PROPERTIES)
                .run(args);

        log.trace("trace log");
        log.debug("debug log");
        log.info("MAIN START - PID: {}", PID);
        log.warn("warning log");
        log.error("error log");
    }
}
