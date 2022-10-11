//package elderlysitter.capstone.sercurity;
//
//import lombok.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class Config implements WebMvcConfigurer {
//
//
//    private String[] inBrowserAllowedOrigins = {"/**"};
//
//    private final String[] inBrowserAllowedMethods = new String[]{"POST","GET","PUT","PATCH", "OPTIONS"};
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/in-browser/login")
//                .allowedOrigins(inBrowserAllowedOrigins)
//                .allowedMethods(inBrowserAllowedMethods);
//        registry.addMapping("/**").allowedOrigins();
//    }
//}