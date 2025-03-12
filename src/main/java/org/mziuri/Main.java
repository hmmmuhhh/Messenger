package org.mziuri;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.mziuri.servlet.DataServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcatSvr");
        tomcat.setPort(8080);

        // Set context path and document base
        String contextPath = "";
        String docBase = new File("./src/main/webapp").getAbsolutePath();

        // Create context
        Context context = tomcat.addContext(contextPath, new File(docBase).getAbsolutePath());

        // Add DefaultServlet to handle static files (including index.html)
        Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
        context.addServletMappingDecoded("/", "default");

        // Add your DataServlet
        Tomcat.addServlet(context, "dataServlet", new DataServlet());
        context.addServletMappingDecoded("/data", "dataServlet");

        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();
    }
}