package com.timbailmu.content;

import java.util.function.Consumer;

import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import com.timbailmu.content.handler.DigitalTasbihHandler;
import static io.javalin.apibuilder.ApiBuilder.*;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.util.JavalinLogger;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create(getConfig()).start();
        app.before(ctx -> {
            JavalinLogger.info(ctx.headerMap().toString());
        });
        app.get("/async-req", ctx -> {
            ctx.async(() -> {
                Thread.sleep(4000);
                ctx.result("OK Nih");
            });
        });
    }

    public static Consumer<JavalinConfig> getConfig() {
        return (config) -> {
            config.useVirtualThreads = false;
            config.http.asyncTimeout = 10_000L;
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/assets";                 // change to host files on a subpath, like '/assets'
                staticFiles.directory = "/public";                  // the directory where your files are located
                staticFiles.location = Location.CLASSPATH;          // Location.CLASSPATH (jar) or Location.EXTERNAL (file system)
                staticFiles.precompress = false;                    // if the files should be pre-compressed and cached in memory (optimization)
                staticFiles.aliasCheck = null;                      // you can configure this to enable symlinks (= ContextHandler.ApproveAliases())
            });
            config.router.apiBuilder(() -> {
                crud("/digital_tasbih/{content-id}", new DigitalTasbihHandler());
            });
            // config.jetty.defaultHost = "localhost"; // set the default host for Jetty
            config.jetty.defaultPort = 7070; // set the default port for Jetty
            config.jetty.threadPool = new QueuedThreadPool(10, 2); // set the thread pool for Jetty    
        };
    }
}
