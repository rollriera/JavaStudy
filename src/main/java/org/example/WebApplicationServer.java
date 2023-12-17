package org.example;


import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebApplicationServer {

    private static final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);

    public static void main(String[] args) throws Exception {

        String webappDirLocation = "webapps/";

        Tomcat tomcat = new Tomcat();

        Connector connector = new Connector();

        connector.setPort(8081);

        tomcat.getService().addConnector(connector);

        tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        log.info("configuring app with basedir: {}", new File("/" + webappDirLocation).getAbsolutePath());

        tomcat.start();

        tomcat.getServer().await();
    }

}

