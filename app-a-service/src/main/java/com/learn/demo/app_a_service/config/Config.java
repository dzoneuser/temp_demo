package com.learn.demo.app_a_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class Config {
    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl("http://localhost:8081").build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/actuator/**").permitAll())
                .authorizeHttpRequests(requests -> requests.requestMatchers("/api/v1/**").permitAll())
               .authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
                //.authorizeHttpRequests(requests -> requests.anyRequest().authenticated()).oauth2Login(withDefaults());
        return httpSecurity.build();
    }

}
