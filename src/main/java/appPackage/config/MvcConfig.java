package appPackage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/imgUploads/", "classpath:/static/uploads/" };


    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:///"+uploadPath+"/");
//                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false);
    }


}
