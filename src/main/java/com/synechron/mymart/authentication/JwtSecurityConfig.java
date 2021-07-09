package com.synechron.mymart.authentication;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

@EnableWebSecurity
@EnableGlobalMethodSecurity(
	    securedEnabled = true,
	    jsr250Enabled = true,
	    prePostEnabled = true
	)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Autowired 
	private CustomUserDetailsService customUserDetailsSrervice;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(customUserDetailsSrervice).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity = httpSecurity.cors().and().csrf().disable();
		
		httpSecurity = httpSecurity.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and();
		
		httpSecurity = httpSecurity.exceptionHandling()
						.authenticationEntryPoint((request, response,ex) -> 
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())
						).and();
		
		httpSecurity.authorizeRequests()
					.antMatchers("/user/**").permitAll()
					.antMatchers("/login/**").permitAll()
					.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", 
							"/configuration/security", "/swagger-ui/**", "/webjars/**", 
							"/swagger-resources/configuration/ui", 
							"/swagger-resources/configuration/security").permitAll()
					.antMatchers(HttpMethod.POST,"/login/").permitAll()
					.antMatchers(HttpMethod.POST,"/user/").permitAll()
					.anyRequest().authenticated();
		
		httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	 @Bean
	    public CorsFilter corsFilter() {
	        var source =
	            new UrlBasedCorsConfigurationSource();
	        var config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("*");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
	
	
}
