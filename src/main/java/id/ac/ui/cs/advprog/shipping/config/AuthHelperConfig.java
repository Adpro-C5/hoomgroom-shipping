package id.ac.ui.cs.advprog.shipping.config;

import id.ac.ui.cs.advprog.shipping.helper.AuthHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AuthHelperConfig{
    @Bean
    public AuthHelper authHelper() {
        return new AuthHelper();
    }
}
