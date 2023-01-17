package com.backend.blog.sprinfsecurity.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.backend.blog.jwt.JWTAuthenticationEntryPoint;
import com.backend.blog.jwt.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	public static final String[] PUBLIC_URL = { "/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
			"/swagger-resources/**", "/swagger-ui/**", "/web-jars/**"

	};

	

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().antMatchers(PUBLIC_URL).permitAll().antMatchers(HttpMethod.GET)
				.permitAll().anyRequest().authenticated().and()
				// agar unauthorized user ata he toh yeh chalgea
				.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println(auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(encode()));
	}

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	// we will use when we will implement the login feature
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	 @Bean
	    public FilterRegistrationBean coresFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.addAllowedOriginPattern("*");
	        corsConfiguration.addAllowedHeader("Authorization");
	        corsConfiguration.addAllowedHeader("Content-Type");
	        corsConfiguration.addAllowedHeader("Accept");
	        corsConfiguration.addAllowedMethod("POST");
	        corsConfiguration.addAllowedMethod("GET");
	        corsConfiguration.addAllowedMethod("DELETE");
	        corsConfiguration.addAllowedMethod("PUT");
	        corsConfiguration.addAllowedMethod("OPTIONS");
	        corsConfiguration.setMaxAge(3600L);

	        source.registerCorsConfiguration("/**", corsConfiguration);

	        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

	        bean.setOrder(-110);

	        return bean;
	    }

}
