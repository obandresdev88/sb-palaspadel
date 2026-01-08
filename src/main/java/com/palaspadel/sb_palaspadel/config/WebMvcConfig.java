
package com.palaspadel.sb_palaspadel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:src/main/resources/images}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Servir imágenes desde el sistema de ficheros (ruta configurada en app.upload.dir)
        // y como fallback desde classpath:/images/ (recursos empaquetados)
        try {
            String fsPath = java.nio.file.Paths.get(uploadDir).toAbsolutePath().toString();
            if (!fsPath.endsWith(java.io.File.separator)) {
                fsPath = fsPath + java.io.File.separator;
            }
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:" + fsPath, "classpath:/images/");
        } catch (Exception e) {
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("classpath:/images/");
        }
    }

    @Value("${frontend.url:https://obandresdev88.github.io}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        frontendUrl,                    // GitHub Pages (producción)
                        "http://localhost:5500",        // Desarrollo local (Live Server)
                        "http://127.0.0.1:5500",
                        "http://localhost:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .maxAge(3600);
    }
}
