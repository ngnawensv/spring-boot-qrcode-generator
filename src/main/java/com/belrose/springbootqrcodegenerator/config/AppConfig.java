package com.belrose.springbootqrcodegenerator.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppConfig {
    @Value("${app.parameters.width}")
    Integer width;
    @Value("${app.parameters.height}")
    Integer height;
}
