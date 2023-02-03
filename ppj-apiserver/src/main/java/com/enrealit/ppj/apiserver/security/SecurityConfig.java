package com.enrealit.ppj.apiserver.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.enrealit.ppj.apiserver.security.jwt.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		LOGGER.debug("Registering CORS filter");
		config.setAllowCredentials(false);
		// TODO A bien configurer à la fin du dev
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Configure Ignore Security Matchers
		web.ignoring()
				// Authorize OPTIONS
				.antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// Add corsFilter
				.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
				// Add tokenAuthenticationFilter
				.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

				// Configure session : STATELESS
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// Disable csrf/formlogin/httpBasic/frameOptions
				.csrf().disable()

				.formLogin().disable().httpBasic().disable().headers().frameOptions().disable().and()
				// Authorize graphiql/graphql"
				.authorizeRequests().antMatchers("/vendor/**", "/graphiql", "/graphql").permitAll().anyRequest()
				.authenticated();
	}
}
