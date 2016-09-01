package com.optimusDev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.optimusDev.security.TpbRestAuthenticationEntryPoint;
import com.optimusDev.security.TpbRestAuthenticationProvider;
import com.optimusDev.security.TpbRestSecurityFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
		//builder.userDetailsService(userDetailsService());	
		//builder.inMemoryAuthentication().withUser("kenhiti").password("123").authorities("DEVELOPER");
		
		builder.authenticationProvider(tpbAuthenticationProvider());
	}	 
	
	
	protected void configure(HttpSecurity http) throws Exception{
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated()
		.and()
			.httpBasic()
			.authenticationEntryPoint(tpbAuthenticationEntryPoint())			
		.and()	
			.csrf().disable()
			.addFilterAfter(tpbRestSecurityFilter(authenticationManagerBean()) , UsernamePasswordAuthenticationFilter.class);
			
	}
	
	/*@Bean
	public UserDetailsService userDetailsService() {
		return new SystemUserDetailsService();
	}*/
	
	@Bean
	public AuthenticationEntryPoint tpbAuthenticationEntryPoint(){		
		return new TpbRestAuthenticationEntryPoint();
	}
	
	@Bean
	public AuthenticationProvider tpbAuthenticationProvider(){
		return new TpbRestAuthenticationProvider();
	}
	
	@Bean
	public TpbRestSecurityFilter tpbRestSecurityFilter(AuthenticationManager manager){
		return new TpbRestSecurityFilter(manager);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
}