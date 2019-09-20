package com.romanov.auth.security;

import com.romanov.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@Configuration
@Primary
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private UserService userDetailsService;
    private TokenStore tokenStore;
//    private DefaultTokenServices tokenServices;
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    @Autowired
    public JWTOAuth2Config(AuthenticationManager authenticationManager,
                           UserService userDetailsService,
                           TokenStore tokenStore,
//                           DefaultTokenServices tokenServices,
                           JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
//        this.tokenServices = tokenServices;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .tokenStore(  tokenStore )
                .accessTokenConverter( jwtAccessTokenConverter )
                .authenticationManager( authenticationManager )
                .userDetailsService( userDetailsService )
                .tokenEnhancer( jwtAccessTokenConverter );
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("ui")
                .secret("secret")
                .authorizedGrantTypes(
                        "refresh_token",
                        "password",
                        "client_credentials"
                ).scopes("webclient");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }
}
