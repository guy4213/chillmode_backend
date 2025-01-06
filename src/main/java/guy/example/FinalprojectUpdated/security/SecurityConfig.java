package guy.example.FinalprojectUpdated.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import guy.example.FinalprojectUpdated.config.RSAKeyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RSAKeyProperties keyProperties;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            //allow AuthController login/register
                            auth.requestMatchers("/api/v1/auth/**").permitAll();

                            //secure the rest of the API
                            auth.requestMatchers("/api/v1/**").permitAll();

                            //  permit any request that does not start with /api/v1
                            auth.anyRequest().permitAll(); //docs  //swagger
                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .oauth2ResourceServer(auth -> auth.jwt(jwtConfigurer -> {
                    var jwtConverter = new JwtAuthenticationConverter();
                    var jwtAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                    jwtAuthoritiesConverter.setAuthorityPrefix("");
                    jwtConverter.setJwtGrantedAuthoritiesConverter(jwtAuthoritiesConverter);
                    jwtConfigurer.jwtAuthenticationConverter(jwtConverter);
                }))
//                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5173", "http://localhost:5173", "http://localhost:5174"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }







    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        RSAKey rsaKey = new RSAKey.Builder(keyProperties.publicKey())
                .privateKey(keyProperties.privateKey())
                .build();

        //JSON Web Key (JWK) set. Represented by a JSON object that contains an array of JSON Web Keys
        var jwKeySet = new JWKSet(rsaKey);

        //JSON Web Key (JWK) source backed by an immutable JWK set.
        //the security context is used to pass additional parameters to the JWK source, such as the JWS algorithm restrictions
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(jwKeySet);

        //the encoder requires a JWKSource and a SecurityContext
        //finally we can create the encoder:
        return new NimbusJwtEncoder(jwkSource);
    }
}
