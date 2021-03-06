package com.cursospring.springboot.di.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import com.cursospring.springboot.di.app.auth.handler.LoginSuccessHandler;
import com.cursospring.springboot.di.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/locale","/","/css/**","/js/**","/images/**","/listar").permitAll()
		//.antMatchers("/ver/**").hasAnyRole("USER")
		//.antMatchers("/upload/**").hasAnyRole("USER")
		//.antMatchers("/form/**").hasAnyRole("ADMIN")
		//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		//.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
				.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}



	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		/*builder
			.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder)
			.usersByUsernameQuery("select username, password, enabled from users where username =?")
			.authoritiesByUsernameQuery(" select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?")
			;*/
		/*PasswordEncoder encoder = passwordEncoder;
		//UserBuilder users = User.builder().passwordEncoder(password -> encoder.encode(password));
		// o puede ser asi
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		builder.inMemoryAuthentication()
			.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
			.withUser(users.username("gustavo").password("12345").roles("USER"))
			;
		*/
		
		builder.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
		
	}
	
	
}
