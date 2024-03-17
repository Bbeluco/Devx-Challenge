package devx.challenge.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserAuthenticationFilter userAuthenticationFilter;

  public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = { "/login", "/mfa", "/password" };
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/mfa"), new AntPathRequestMatcher("/password")).permitAll()
      )
      .authorizeHttpRequests(
        authorize -> authorize.anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
      .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
