package gov.uk.api.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class GatewayConfiguration {
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

}
