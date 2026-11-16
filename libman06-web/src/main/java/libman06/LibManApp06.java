package libman06;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class LibManApp06 {

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        File base = new File("src/main/webapp").isDirectory() ? new File(".") : new File("libman06-web");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(new File(base, "target/tomcat").getAbsolutePath());
        tomcat.getConnector();

        Context ctx = tomcat.addWebapp("", new File(base, "src/main/webapp").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                new File(base, "target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        System.out.println();
        System.out.println("LibMan06 is running:  http://localhost:" + port + "/");
        System.out.println("Staff login:          hoangva / 123456");
        System.out.println("Press Ctrl+C to stop.");
        tomcat.getServer().await();
    }
}
