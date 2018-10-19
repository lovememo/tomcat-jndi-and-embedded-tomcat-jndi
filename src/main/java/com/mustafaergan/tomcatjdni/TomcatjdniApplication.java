package com.mustafaergan.tomcatjdni;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class TomcatjdniApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomcatjdniApplication.class, args);
    }


    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                // context
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/blabla");
                resource.setAuth("Container");
                resource.setType("javax.sql.DataSource");
                resource.setProperty("driverClassName", "oracle.jdbc.driver.OracleDriver");
                resource.setProperty("url", "jdbc:oracle:thin:@bla.bla.COM:1521/bla");
                resource.setProperty("username", "kul");
                resource.setProperty("password", "sifre");
                context.getNamingResources().addResource(resource);
            }
        };
    }

}
