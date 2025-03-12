//package org.mziuri;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.startup.Tomcat;
//import org.mziuri.servlet.DataServlet;
//
//import java.io.File;
//
//public class Main {
//    public static void main(String[] args) throws LifecycleException {
//        Tomcat tomcat = new Tomcat();
//        tomcat.setBaseDir("tomcatSvr");
//        tomcat.setPort(8080);
//
//        // Set context path and document base
//        String contextPath = "";
//        String docBase = new File("./src/main/webapp").getAbsolutePath();
//
//        // Create context
//        Context context = tomcat.addContext(contextPath, new File(docBase).getAbsolutePath());
//
//        // Add DefaultServlet to handle static files (including index.html)
//        Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
//        context.addServletMappingDecoded("/", "default");
//
//        // Add your DataServlet
//        Tomcat.addServlet(context, "dataServlet", new DataServlet());
//        context.addServletMappingDecoded("/data", "dataServlet");
//
//        tomcat.start();
//        tomcat.getConnector();
//        tomcat.getServer().await();
//    }
//}

package org.mziuri;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.mziuri.servlet.MessageServlet;
import org.mziuri.servlet.UserServlet;
import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcatSvr");
        tomcat.setPort(8080);

        Context context = tomcat.addContext("", new File("./src/main/webapp").getAbsolutePath());

        Tomcat.addServlet(context, "messageServlet", new MessageServlet());
        context.addServletMappingDecoded("/message", "messageServlet");

        Tomcat.addServlet(context, "userServlet", new UserServlet());
        context.addServletMappingDecoded("/user", "userServlet");

        Tomcat.addServlet(context, "default", new org.apache.catalina.servlets.DefaultServlet());
        context.addServletMappingDecoded("/", "default");

        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();
    }
}