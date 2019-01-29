package com.gx.demo.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	/**
	 * 配置资源的访问
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home").permitAll() // 登录页面所有人都可以访问
				.antMatchers("/admin").hasRole("ADMIN")// admin的页面只有admin的角色可以访问
				.anyRequest().authenticated()
				.and()
				.formLogin() // 指定支持基于表单的身份验证。 如果未指定则生成默认的登录界面，这个页面它必须是HTTP POST
				// 它必须提交给AbstractAuthenticationFilterConfigurer.loginProcessingUrl（String）
				// 它应该包含用户名作为HTTP参数的usernameParameter（String）名称
				// 它应该将密码作为HTTP参数包含在passwordParameter（String）的名称中
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.permitAll();
		http.exceptionHandling().accessDeniedPage("/403");
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//				.inMemoryAuthentication() // 把用户的权限访问存在内存中
//				.passwordEncoder(new BCryptPasswordEncoder()) // 密码必须加密，否则不能通过验证
//				.withUser("user")
//				.password(new BCryptPasswordEncoder().encode("user"))
//				.roles("USER")
//				.and()
//				.withUser("admin")
//				.password(new BCryptPasswordEncoder().encode("admin"))
//				.roles("ADMIN");
//	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}

}
