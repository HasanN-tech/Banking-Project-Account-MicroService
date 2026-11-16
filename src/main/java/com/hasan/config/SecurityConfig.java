package com.hasan.config;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/health").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

						// Customer's own account list
						.requestMatchers(HttpMethod.GET, "/api/accounts/my-accounts").hasRole("CUSTOMER")

						// Staff-only account administration
						.requestMatchers(HttpMethod.GET, "/api/accounts/all", "/api/accounts/all/**",
								"/api/accounts/customer/**")
						.hasAnyRole("ADMIN", "BANK_MANAGER", "EMPLOYEE")

						.requestMatchers(HttpMethod.PUT, "/api/accounts/*/freeze", "/api/accounts/*/activate",
								"/api/accounts/*/close")
						.hasAnyRole("ADMIN", "BANK_MANAGER", "EMPLOYEE")

						// Require a valid token for every other API.
						.anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

		return http.build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

		// Auth Service JWT must contain role: "CUSTOMER", "ADMIN", etc.
		authoritiesConverter.setAuthoritiesClaimName("role");
		authoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

		converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

		return converter;
	}

	@Bean
	JwtDecoder jwtDecoder() throws IOException {
		ClassPathResource publicKeyResource = new ClassPathResource("keys/public-key.pem");

		RSAPublicKey publicKey = RsaKeyConverters.x509().convert(publicKeyResource.getInputStream());

		NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

		OAuth2TokenValidator<org.springframework.security.oauth2.jwt.Jwt> validator = JwtValidators
				.createDefaultWithIssuer("banking-auth-service");

		decoder.setJwtValidator(validator);

		return decoder;
	}
}
