package com.asiapay.security;

import com.asiapay.configuration.userconfigure.MyUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


/**
 * Description:
 * @EnableWebSecurity： 启用Spring安全Web安全支持。
 * @EnableGlobalMethodSecurity： 支持具有方法级访问控制，例如@PreAuthorize @PostAuthorize
 */

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Log logger = LogFactory.getLog(OAuth2SecurityConfiguration.class);

	@Autowired
	DataSource dataSource;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private MyUserDetailsService userDetailsService;



	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider
				= new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		//authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}


	/**
	 * 加密方式
	 * @return
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}


	/**
	 * user 授权验证
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {

		/*auth.inMemoryAuthentication()
        .withUser("bill").password("abc123").roles("ADMIN").and()
        .withUser("bob").password("abc123").roles("USER");*/
		/*auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select user_name,password,age from user_t where user_name = ?")
				.passwordEncoder(NoOpPasswordEncoder.getInstance());*/

		auth.authenticationProvider(authenticationProvider());


	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.anonymous().disable()
	  	.authorizeRequests()
	  	.antMatchers("/oauth/token").permitAll();
    }


	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}
	
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	
}
