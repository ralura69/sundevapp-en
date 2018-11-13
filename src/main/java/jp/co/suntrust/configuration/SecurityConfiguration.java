package jp.co.suntrust.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	private final String USER_QUERY = "SELECT username, `password`, enabled FROM `m_user` WHERE username = ?";
	private final String AUTH_QUERY = "SELECT username, authority FROM authorities WHERE username = ?";

	//	@Override
	//	public void configure(WebSecurity web) throws Exception {
	//		web.ignoring().antMatchers("/bootstrap/css/**", "/bootstrap/img/**", "/bootstrap/js/**");
	//	}

	//	@Override
	//	protected void configure(HttpSecurity http) throws Exception {
	//		http.authorizeRequests().antMatchers("/").permitAll()
	//				.antMatchers("/admin/**").hasRole("ADMIN")
	//				.anyRequest().authenticated().and().csrf().disable()
	//
	//				.formLogin().loginPage("/login").loginProcessingUrl("/doLogin").failureUrl("/login?error=true")
	//				.defaultSuccessUrl("/userList");
	//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("AuthenticationManagerBuilder");
		auth.jdbcAuthentication()
				.usersByUsernameQuery(USER_QUERY)
				.authoritiesByUsernameQuery(AUTH_QUERY)
				.dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("HttpSecurity");
		http.authorizeRequests()
				.antMatchers("/bootstrap/css/**", "/bootstrap/img/**", "/bootstrap/js/**").permitAll()
				.antMatchers("/").permitAll()

			.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/doLogin")
				.usernameParameter("userID")
				.passwordParameter("password")
				.permitAll()

			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.permitAll()
//				.antMatchers("/").permitAll()
//				.antMatchers("/home/**").hasAuthority("ADMIN").anyRequest()
//				.authenticated().and().csrf().disable()
//				.formLogin()
//				.loginPage("/login")
//				.loginProcessingUrl("/doLogin")
//				.failureUrl("/login?error=true")
//				.defaultSuccessUrl("/list")
//				.usernameParameter("userID")
//				.passwordParameter("password")
//				.and().logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				.logoutSuccessUrl("/")
//				.and().rememberMe()
//				.tokenRepository(persistentTokenRepository())
//				.tokenValiditySeconds(60 * 60)
//				.and().exceptionHandling().accessDeniedPage("/access_denied")
				;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		log.info("persistentTokenRepository");
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

}
