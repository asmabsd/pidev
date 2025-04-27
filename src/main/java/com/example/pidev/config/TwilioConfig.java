package com.example.pidev.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Getter
@Setter
public class TwilioConfig {





    private String account_sid="AC738b9ede8c95d61cf40890a117841897";
    private String auth_token="2fb551d3001cd9d85e021782c07e98ae";
    private String from_number="whatsapp:+14155238886";
}
