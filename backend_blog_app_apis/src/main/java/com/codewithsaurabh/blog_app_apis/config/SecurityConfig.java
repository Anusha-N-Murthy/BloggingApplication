package com.codewithsaurabh.blog_app_apis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithsaurabh.blog_app_apis.security.CustomUserDetailService;
import com.codewithsaurabh.blog_app_apis.security.JwtAuthenticationEntryPoint;
import com.codewithsaurabh.blog_app_apis.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc // Adding this annotation to an @Configuration class imports the Spring
				// MVCconfiguration from WebMvcConfigurationSupport,
//@EnableGlobalMethodSecurity(prePostEnabled = true) //For userwise  Access
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public static final String[] PUBLIC_URLS = {"/api/**", "/api/auth/**", "/v3/api-docs", "/v2/api-docs",
			"/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };

	// For Basic authentication
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// For Basic Authentication
//	http
//	 	.csrf().disable()
//	 	.authorizeHttpRequests()
//	 	.anyRequest()
//	 	.authenticated()
//	 	.and()
//	 	.httpBasic();

		// For JWT Token
		http.csrf().disable().authorizeHttpRequests()
//	 	.antMatchers("/api/v1/auth/login").permitAll() //Grant public access login
//	 	.antMatchers("/api/v1/auth/**").permitAll() //Grant public access 
//	 	.antMatchers("/v3/api-docs").permitAll() //Grant public access 
				.antMatchers(PUBLIC_URLS).permitAll() // Grant public access
//	 	.antMatchers("/api/v1/auth/login").hasRole("ADMIN") //Grant ADMIN Only access
				.antMatchers(HttpMethod.GET).permitAll()// Grant public access -Get methods
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
	
	
//	@Bean
//	public FilterRegistrationBean<CorsFilter> corsFilter() {
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    CorsConfiguration config = new CorsConfiguration();
//	    config.setAllowCredentials(true);
//	    config.addAllowedOrigin("*");
//	    config.addAllowedHeader("*");
//	    config.addAllowedMethod("OPTIONS");
//	    config.addAllowedMethod("HEAD");
//	    config.addAllowedMethod("GET");
//	    config.addAllowedMethod("PUT");
//	    config.addAllowedMethod("POST");
//	    config.addAllowedMethod("DELETE");
//	    config.addAllowedMethod("PATCH");
//	    source.registerCorsConfiguration("/**", config);
//	    // return new CorsFilter(source);
//	    final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
//	    bean.setOrder(0);
//	    return bean;
//	}

}