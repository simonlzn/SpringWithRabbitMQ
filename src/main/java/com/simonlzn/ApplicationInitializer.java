package com.simonlzn;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer
        implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        AnnotationConfigWebApplicationContext context = new
                AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);

        servletContext.addListener(
                new ContextLoaderListener(context));

        AnnotationConfigWebApplicationContext dispatchContext =
                new AnnotationConfigWebApplicationContext();
        ServletRegistration.Dynamic dispatcher;

        dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(dispatchContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");


    }

}