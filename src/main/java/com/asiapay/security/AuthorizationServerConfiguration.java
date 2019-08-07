package com.asiapay.security;

import com.asiapay.configuration.AppleUserDetailService;
import com.asiapay.configuration.ApplyClientDetailService;
import com.asiapay.configuration.MyJdbcTokenStore;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;


/**
 * Description: OAuth2授权服务器配置
 *
 * @EnableAuthorizationServer; 启用授权服务器.AuthorizationServerEndpointsConfigurer定义授权和令牌端点以及令牌服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private static final String DEMO_RESOURCE_ID = "*";


	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;


	@Resource
	private DataSource dataSource;

	
	@Autowired
	private TokenStore tokenStore;

	// 初始化JdbcTokenStore
	@Autowired
	public TokenStore getTokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	// 自定义数据库存储tokenStore
	@Autowired
	public TokenStore getMyTokenStore() {
		return new MyJdbcTokenStore(dataSource);
	}

	@Bean   // 声明ApplyClientDetailService
	public ApplyClientDetailService getClientDetails() {
		return new ApplyClientDetailService();
	}




	@Autowired
	private UserApprovalHandler userApprovalHandler;



	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {


		// 配置客户端, 用于client认证
		//clients.withClientDetails(getClientDetails());

		//添加客户端信息
		clients.inMemory()
	        .withClient("my-trusted-client")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust")
            .secret("secret")
            .accessTokenValiditySeconds(60*60).//Access token is only valid for 2 minutes.
            refreshTokenValiditySeconds(600);//Refresh token is only valid for 10 minutes.

	}

	/**
	 *
	 * @param endpoints  配置授权端点 URL（Endpoint URLs）
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
				.authenticationManager(authenticationManager);

		/*endpoints.tokenStore(getTokenStore())   // 数据库保存token
				.authenticationManager(authenticationManager);*/
	}

	@Override

	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		//允许表单认证
		oauthServer.allowFormAuthenticationForClients();
	}

}