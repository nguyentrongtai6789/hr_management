package com.hr_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication
public class HrManagementApplication {

    private final Environment environment;

    public HrManagementApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrManagementApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printStartupInfo() throws Exception {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String localhostUrl = "http://localhost:" + port + contextPath;
        String ipUrl = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath;
        System.out.println("=================================");
        System.out.println("🚀 HR MANAGEMENT STARTED");
        System.out.println("🌐 Local:   " + localhostUrl);
        System.out.println("🌍 Network: " + ipUrl);
        System.out.println("=================================");
    }
}