package org.generation.webproject.security;

//This Class will implement the WebMvcConfigurer interface provided by Spring Boot
// Framework
//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html

//This Class is to perform action on URL Routing and mapping when a HTTP request comes in
//E.g. if user key in localhost:8080 in the browser, browser will send a HTTP GET
// request to the server (back-end). The back-end will need to handle which HTML to
// response back to the browser (client) - index.html

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.*;


@Configuration
public class MvcConfig  implements WebMvcConfigurer
{
    @Value("${image.folder}")
    private String imageFolder;
    //now imageFolder variable the value = productImages


    //addViewControllers is a method provided by WebMvcConfigurer interface
    public void addViewControllers(ViewControllerRegistry registry) {
        //Map the browser's URL to a specific View (HTML) inside resources/templates directory
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/aboutus").setViewName("aboutus");
        registry.addViewController("/products").setViewName("products");
        registry.addViewController("/productform").setViewName("productform");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("index");
    }





    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //browser is an external client that tries to access the files from the server
        //To allow external client to access the files from the static folder due to
        // security reason
        // (e.g. file such as images, videos, sound, js, css)
        registry.addResourceHandler("/static")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
        //setCachePeriod(0) - prevent cache of files (e.g images) in the browser
        // (Client)


        //expose the productImages folder to allow external client to access the files from the
        // server
        Path uploadDir = Paths.get(imageFolder);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/" + imageFolder + "/**")
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(0);

    }
}

