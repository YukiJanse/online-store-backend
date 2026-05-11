package se.jensen.yuki.onlinestorebackend.shared.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {
    @Bean
    public WebClient webClient(@Value("${webclient.url}") String url) {
        log.debug("WebClient base url: {}", url);
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}
