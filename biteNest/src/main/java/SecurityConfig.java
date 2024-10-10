import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
            .authorizeHttpRequests(authz -> authz
            		.requestMatchers("/login").permitAll()  // 로그인 API는 인증 없이 접근 가능
            		.anyRequest().authenticated()  // 그 외 요청은 인증 필요
            )
            .oauth2ResourceServer(oauth2 -> oauth2
            		.jwt(jwt -> jwt.decoder(jwtDecoder()))
            		
            );
            return http.build();
    }
    
    //JWT Decoder
    @Bean
    public JwtDecoder jwtDecoder() {
    	
    	return NimbusJwtDecoder.withJwkSetUri("").build();
    }
}