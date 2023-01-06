package ru.job4j.shortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Job4jUrlShortcutApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Job4jUrlShortcutApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Job4jUrlShortcutApplication.class, args);
    }
}
