package spring.sample.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringSampleRestApplication {
    private static final Logger LOG = LoggerFactory.getLogger(SpringSampleRestApplication.class);

    public static void main(String[] args) {
        LOG.debug("-- START --");
        SpringApplication.run(SpringSampleRestApplication.class, args);
    }
}
