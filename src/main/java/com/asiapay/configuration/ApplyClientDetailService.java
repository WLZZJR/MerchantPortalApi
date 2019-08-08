package com.asiapay.configuration;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

public class ApplyClientDetailService implements ClientDetailsService {



    @Autowired
    private DataSource dataSource;

    public ClientDetails loadClientByClientId(String applyName) throws ClientRegistrationException {

        JdbcClientDetailsService jdbcClientDetailsService= new JdbcClientDetailsService(dataSource);
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(applyName);

        return clientDetails;
    }
}
